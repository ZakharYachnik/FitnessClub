package by.zakharyachnik.fitnessclub.dto;

public record PersonalTrainingDto(
    Long id,
    UserDto customerDto,
    Long trainerId,
    TrainingProgramDto trainingProgramDto,
    HealthyEatingProgramDto healthyEatingProgramDto
) {
}
