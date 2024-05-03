package by.zakharyachnik.fitnessclub.service.impl;

import by.zakharyachnik.fitnessclub.dto.UserDto;
import by.zakharyachnik.fitnessclub.mapper.UserMapper;
import by.zakharyachnik.fitnessclub.repository.UserRepository;
import by.zakharyachnik.fitnessclub.repository.UserRoleRepository;
import by.zakharyachnik.fitnessclub.service.TrainerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class TrainerServiceImpl implements TrainerService {

    private final UserRepository userRepository;

    private final UserRoleRepository userRoleRepository;

    private final UserMapper userMapper;
    @Override
    public List<UserDto> findAll() {
        return userRepository.findByRoleName("ROLE_TRAINER")
                .stream()
                .map(userMapper::toUserDto)
                .toList();
    }

}
