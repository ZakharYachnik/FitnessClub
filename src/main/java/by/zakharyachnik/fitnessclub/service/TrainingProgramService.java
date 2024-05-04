package by.zakharyachnik.fitnessclub.service;

import by.zakharyachnik.fitnessclub.dto.TrainingProgramDto;
import by.zakharyachnik.fitnessclub.entity.TrainingProgram;
import by.zakharyachnik.fitnessclub.exceptions.NotFoundException;

import java.util.List;

public interface TrainingProgramService {
    List<TrainingProgramDto> findAll();

    TrainingProgramDto save(String name, String description);

    TrainingProgramDto findById(Long trainingProgramId) throws NotFoundException;

    TrainingProgramDto update(String name, String description, Long trainingProgramId) throws NotFoundException;
}
