package by.zakharyachnik.fitnessclub.repository;

import by.zakharyachnik.fitnessclub.entity.HealthyEatingProgram;
import by.zakharyachnik.fitnessclub.entity.Membership;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MembershipRepository extends JpaRepository<Membership, Long> {
}
