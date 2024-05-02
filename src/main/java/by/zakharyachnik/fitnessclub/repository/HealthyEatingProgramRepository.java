package by.zakharyachnik.fitnessclub.repository;

import by.zakharyachnik.fitnessclub.entity.HealthyEatingProgram;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HealthyEatingProgramRepository extends JpaRepository<HealthyEatingProgram, Long> {

}
