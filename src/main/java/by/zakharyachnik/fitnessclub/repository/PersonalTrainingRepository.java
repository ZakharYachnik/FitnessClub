package by.zakharyachnik.fitnessclub.repository;

import by.zakharyachnik.fitnessclub.entity.HealthyEatingProgram;
import by.zakharyachnik.fitnessclub.entity.PersonalTraining;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonalTrainingRepository extends JpaRepository<PersonalTraining, Long> {
}
