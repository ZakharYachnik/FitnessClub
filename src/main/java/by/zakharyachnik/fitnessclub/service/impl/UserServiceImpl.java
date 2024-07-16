package by.zakharyachnik.fitnessclub.service.impl;

import by.zakharyachnik.fitnessclub.dto.UserDto;
import by.zakharyachnik.fitnessclub.entity.User;
import by.zakharyachnik.fitnessclub.entity.UserRole;
import by.zakharyachnik.fitnessclub.exceptions.AlreadyExistsException;
import by.zakharyachnik.fitnessclub.exceptions.NotFoundException;
import by.zakharyachnik.fitnessclub.mapper.UserMapper;
import by.zakharyachnik.fitnessclub.repository.UserRepository;
import by.zakharyachnik.fitnessclub.repository.UserRoleRepository;
import by.zakharyachnik.fitnessclub.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final UserMapper userMapper;

    private final PasswordEncoder passwordEncoder;

    private final UserRoleRepository userRoleRepository;

    @Override
    public List<UserDto> findAll() {
        return userRepository.findAll()
                .stream()
                .map(userMapper::toUserDto)
                .toList();
    }

    @Override
    public UserDto save(String username, String fullName, String password, String phoneNumber, String role) throws AlreadyExistsException {
        if(userRepository.findByUsername(username).isPresent()){
            throw new AlreadyExistsException("User with username " + username + " already exists");
        }

        User user = new User()
                .setUsername(username)
                .setPassword(passwordEncoder.encode(password))
                .setFullName(fullName)
                .setActive(true)
                .setPhoneNumber(phoneNumber)
                .setUserRoles(Set.of(userRoleRepository.findByName(role)));
        return userMapper.toUserDto(userRepository.save(user));
    }

    @Override
    public UserDto blockUser(String username) throws NotFoundException {
        Optional<User> user = userRepository.findByUsername(username);
        if(user.isEmpty()){
            throw new NotFoundException("User with username " + username + " not found");
        }

        user.get().setActive(false);
        return userMapper.toUserDto(userRepository.save(user.get()));
    }

    @Override
    public UserDto unblockUser(String username) throws NotFoundException {
        Optional<User> user = userRepository.findByUsername(username);
        if(user.isEmpty()){
            throw new NotFoundException("User with username " + username + " not found");
        }

        user.get().setActive(true);
        return userMapper.toUserDto(userRepository.save(user.get()));
    }

    @Override
    public UserDto update(String username, String fullName, String password, String phoneNumber, Long userId) throws AlreadyExistsException, NotFoundException {
        if(userRepository.findByUsernameAndIdNot(username, userId).isPresent()){
            throw new AlreadyExistsException("User with username " + username + " and another id already exists");
        }

        Optional<User> user = userRepository.findById(userId);

        if(user.isEmpty()){
            throw new NotFoundException("User with id " + userId + " not found");
        }

        user.get().setUsername(username)
                .setFullName(fullName)
                .setPassword(passwordEncoder.encode(password))
                .setPhoneNumber(phoneNumber);
        return userMapper.toUserDto(userRepository.save(user.get()));
    }

    @Override
    public UserDto findById(Long id) {
        return userMapper.toUserDto(userRepository.findById(id).orElse(null));
    }
}
