package by.zakharyachnik.fitnessclub.repository;

import by.zakharyachnik.fitnessclub.entity.PersonalTraining;
import by.zakharyachnik.fitnessclub.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PersonalTrainingRepository extends JpaRepository<PersonalTraining, Long> {
    Optional<PersonalTraining> findByTrainerIdAndCustomerIdAndActive(Long trainerId, Long customerId, Boolean active);

    List<PersonalTraining> findByCustomerIdAndActive(Long customerId, Boolean active);

    List<PersonalTraining> findByTrainerIdAndActive(Long trainerId, Boolean active);
}
