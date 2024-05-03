package by.zakharyachnik.fitnessclub.service;

import by.zakharyachnik.fitnessclub.dto.UserDto;
import by.zakharyachnik.fitnessclub.entity.User;

import java.util.List;

public interface TrainerService {
    List<UserDto> findAll();

}
