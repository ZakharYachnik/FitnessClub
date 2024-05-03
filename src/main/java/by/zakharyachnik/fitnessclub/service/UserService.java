package by.zakharyachnik.fitnessclub.service;

import by.zakharyachnik.fitnessclub.dto.UserDto;
import by.zakharyachnik.fitnessclub.entity.User;
import by.zakharyachnik.fitnessclub.exceptions.AlreadyExistsException;
import by.zakharyachnik.fitnessclub.exceptions.NotFoundException;

import java.util.List;

public interface UserService {
    List<UserDto> findAll();

    UserDto save(String username, String fullName, String password, String phoneNumber, String role) throws AlreadyExistsException;

    UserDto blockUser(String username) throws NotFoundException;

    UserDto unblockUser(String username) throws NotFoundException;

    UserDto update(String username, String fullName, String password, String phoneNumber, Long userId) throws AlreadyExistsException, NotFoundException;

    UserDto findById(Long id);
}
