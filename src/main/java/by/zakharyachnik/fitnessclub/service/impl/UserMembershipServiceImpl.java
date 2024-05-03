package by.zakharyachnik.fitnessclub.service.impl;

import by.zakharyachnik.fitnessclub.dto.UserMembershipDto;
import by.zakharyachnik.fitnessclub.entity.User;
import by.zakharyachnik.fitnessclub.entity.UserMembership;
import by.zakharyachnik.fitnessclub.exceptions.AlreadyExistsException;
import by.zakharyachnik.fitnessclub.exceptions.NotFoundException;
import by.zakharyachnik.fitnessclub.mapper.UserMembershipMapper;
import by.zakharyachnik.fitnessclub.repository.MembershipRepository;
import by.zakharyachnik.fitnessclub.repository.UserMembershipRepository;
import by.zakharyachnik.fitnessclub.repository.UserRepository;
import by.zakharyachnik.fitnessclub.service.UserMembershipService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserMembershipServiceImpl implements UserMembershipService {

    private final UserMembershipRepository userMembershipRepository;

    private final MembershipRepository membershipRepository;

    private final UserMembershipMapper userMembershipMapper;

    private final UserRepository userRepository;

    @Override
    public UserMembershipDto purchaseMembership(Long membershipId, String username) throws NotFoundException, AlreadyExistsException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new NotFoundException("User with username " + username + " not found"));


        if (userMembershipRepository.findByUserIdAndActive(user.getId(), true).isPresent()) {
            throw new AlreadyExistsException("User with username " + username + " already has a membership");
        }

        UserMembership userMembership = new UserMembership()
                .setMembership(membershipRepository.findById(membershipId)
                        .orElseThrow(() -> new NotFoundException("Membership with id " + membershipId + " not found")))
                .setUser(user)
                .setPurchaseDate(LocalDate.now())
                .setActive(true);

        return userMembershipMapper.toUserMembershipDto(userMembershipRepository.save(userMembership));
    }

    @Override
    public List<UserMembershipDto> findAllActiveMemberships() {
        return userMembershipRepository.findByActive(true)
                .stream()
                .map(userMembershipMapper::toUserMembershipDto)
                .toList();
    }

    @Override
    public UserMembershipDto completeUserMembership(Long userMembershipId) throws NotFoundException {
        UserMembership userMembership = userMembershipRepository.findById(userMembershipId)
                .orElseThrow(() -> new NotFoundException("User membership with id " + userMembershipId + " not found"));

        userMembership.setActive(false);

        return userMembershipMapper.toUserMembershipDto(userMembershipRepository.save(userMembership));
    }

    @Override
    public UserMembershipDto findUserMembershipByUserId(Long userId) throws NotFoundException {
        return userMembershipMapper.toUserMembershipDto(userMembershipRepository.findByUserIdAndActive(userId, true)
                .orElseThrow(() -> new NotFoundException("User membership with user id " + userId + " not found")));
    }
}
