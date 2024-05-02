package by.zakharyachnik.fitnessclub.repository;

import by.zakharyachnik.fitnessclub.entity.User;
import by.zakharyachnik.fitnessclub.entity.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRoleRepository extends JpaRepository<UserRole, String> {

    UserRole findByName(String name);
}
