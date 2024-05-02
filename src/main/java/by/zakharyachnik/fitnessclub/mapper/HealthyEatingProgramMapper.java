package by.zakharyachnik.fitnessclub.mapper;

import by.zakharyachnik.fitnessclub.dto.HealthyEatingProgramDto;
import by.zakharyachnik.fitnessclub.entity.HealthyEatingProgram;
import org.mapstruct.Mapper;

@Mapper
public interface HealthyEatingProgramMapper {

    HealthyEatingProgramDto toHealthyEatingProgramDto(HealthyEatingProgram healthyEatingProgram);

    HealthyEatingProgram toHealthyEatingProgram(HealthyEatingProgramDto healthyEatingProgramDto);
}
