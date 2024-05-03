package by.zakharyachnik.fitnessclub.service;

import by.zakharyachnik.fitnessclub.dto.PersonalTrainingDto;
import by.zakharyachnik.fitnessclub.dto.UserDto;
import by.zakharyachnik.fitnessclub.exceptions.AlreadyExistsException;
import by.zakharyachnik.fitnessclub.exceptions.NotFoundException;

import java.util.List;

public interface PersonalTrainingService {
    PersonalTrainingDto addNewPersonalTraining(Long trainerId, String username) throws NotFoundException, AlreadyExistsException;

     List<UserDto> getUserTrainers(Long userId) throws NotFoundException;

    PersonalTrainingDto cancelPersonalTraining(Long trainerId, Long customerId) throws NotFoundException;

    PersonalTrainingDto cancelPersonalTraining(Long personalTrainingId) throws NotFoundException;
}
