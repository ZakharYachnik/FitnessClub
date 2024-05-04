package by.zakharyachnik.fitnessclub.repository;


import by.zakharyachnik.fitnessclub.entity.UserMembership;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserMembershipRepository extends JpaRepository<UserMembership, Long> {

    Optional<UserMembership> findByUserId(Long userId);

    List<UserMembership> findByActive(Boolean active);

    Optional<UserMembership> findByUserIdAndActive(Long userId, Boolean active);
}
