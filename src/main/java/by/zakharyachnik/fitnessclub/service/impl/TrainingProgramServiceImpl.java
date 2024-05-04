package by.zakharyachnik.fitnessclub.service.impl;

import by.zakharyachnik.fitnessclub.dto.TrainingProgramDto;
import by.zakharyachnik.fitnessclub.entity.TrainingProgram;
import by.zakharyachnik.fitnessclub.exceptions.NotFoundException;
import by.zakharyachnik.fitnessclub.mapper.TrainingProgramMapper;
import by.zakharyachnik.fitnessclub.repository.TrainingProgramRepository;
import by.zakharyachnik.fitnessclub.repository.UserRepository;
import by.zakharyachnik.fitnessclub.service.TrainingProgramService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TrainingProgramServiceImpl implements TrainingProgramService {

    private final TrainingProgramRepository trainingProgramRepository;

    private final TrainingProgramMapper trainingProgramMapper;

    private final UserRepository userRepository;

    @Override
    public List<TrainingProgramDto> findAll() {
        return trainingProgramRepository.findAll()
                .stream()
                .map(trainingProgramMapper::toTrainingProgramDto)
                .toList();
    }

    @Override
    public TrainingProgramDto save(String name, String description) {
        return trainingProgramMapper.toTrainingProgramDto(trainingProgramRepository.save(
                new TrainingProgram()
                        .setName(name)
                        .setDescription(description)
        ));
    }

    @Override
    public TrainingProgramDto findById(Long trainingProgramId) throws NotFoundException {
        return trainingProgramMapper.toTrainingProgramDto(trainingProgramRepository.findById(trainingProgramId)
                .orElseThrow(() -> new NotFoundException("Training program with id " + trainingProgramId + " not found")));
    }

    @Override
    public TrainingProgramDto update(String name, String description, Long trainingProgramId) throws NotFoundException {
        TrainingProgram trainingProgram = trainingProgramRepository.findById(trainingProgramId)
                .orElseThrow(() -> new NotFoundException("Training program with id " + trainingProgramId + " not found"));
        trainingProgram.setName(name);
        trainingProgram.setDescription(description);
        return trainingProgramMapper.toTrainingProgramDto(trainingProgramRepository.save(trainingProgram));
    }
}
