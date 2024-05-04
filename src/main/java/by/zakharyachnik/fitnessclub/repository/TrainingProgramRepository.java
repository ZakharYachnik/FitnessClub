package by.zakharyachnik.fitnessclub.repository;

import by.zakharyachnik.fitnessclub.entity.TrainingProgram;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TrainingProgramRepository extends JpaRepository<TrainingProgram, Long> {
}
