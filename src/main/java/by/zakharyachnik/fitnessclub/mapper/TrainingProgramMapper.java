package by.zakharyachnik.fitnessclub.mapper;

import by.zakharyachnik.fitnessclub.dto.TrainingProgramDto;
import by.zakharyachnik.fitnessclub.entity.TrainingProgram;
import org.mapstruct.Mapper;

@Mapper
public interface TrainingProgramMapper {

    TrainingProgramDto toTrainingProgramDto(TrainingProgram trainingProgram);

    TrainingProgram toTrainingProgram(TrainingProgramDto trainingProgramDto);
}
