package by.zakharyachnik.fitnessclub.repository;

import by.zakharyachnik.fitnessclub.entity.HealthyEatingProgram;
import by.zakharyachnik.fitnessclub.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {

}
