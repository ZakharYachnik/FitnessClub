package by.zakharyachnik.fitnessclub.mapper;


import by.zakharyachnik.fitnessclub.dto.PersonalTrainingDto;
import by.zakharyachnik.fitnessclub.entity.PersonalTraining;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(uses = {UserMapper.class})
public interface PersonalTrainingMapper {

    @Mapping(target = "trainerId", source = "trainer.id")
    @Mapping(target = "customerDto", source = "customer")
    @Mapping(target = "trainingProgramDto", source = "trainingProgram")
    @Mapping(target = "healthyEatingProgramDto", source = "healthyEatingProgram")
    PersonalTrainingDto toPersonalTrainingDto(PersonalTraining personalTraining);

    @Mapping(target = "trainer.id", source = "trainerId")
    @Mapping(target = "customer", source = "customerDto")
    @Mapping(target = "trainingProgram", source = "trainingProgramDto")
    @Mapping(target = "healthyEatingProgram", source = "healthyEatingProgramDto")
    PersonalTraining toPersonalTraining(PersonalTrainingDto personalTrainingDto);
}
