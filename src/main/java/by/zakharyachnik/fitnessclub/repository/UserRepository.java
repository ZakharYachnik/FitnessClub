package by.zakharyachnik.fitnessclub.repository;

import by.zakharyachnik.fitnessclub.entity.HealthyEatingProgram;
import by.zakharyachnik.fitnessclub.entity.User;
import by.zakharyachnik.fitnessclub.entity.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);

    Optional<Object> findByUsernameAndIdNot(String username, Long userId);

    @Query("SELECT u FROM User u JOIN u.userRoles r WHERE r.name = :name")
    List<User> findByRoleName(@Param("name") String roleName);
}
