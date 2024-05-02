package by.zakharyachnik.fitnessclub.mapper;

import by.zakharyachnik.fitnessclub.dto.UserDto;
import by.zakharyachnik.fitnessclub.entity.User;
import by.zakharyachnik.fitnessclub.entity.UserRole;
import by.zakharyachnik.fitnessclub.repository.UserRoleRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper
public abstract class UserMapper {
    protected UserRoleRepository userRoleRepository;

    public abstract UserDto toUserDto(User user);
    public abstract User toUser(UserDto userDto);

    public List<String> map(Set<UserRole> roles) {
        List<String> roleNames = roles
                .stream()
                .map(UserRole::getName)
                .toList();
        return List.copyOf(roleNames);
    }

    public Set<UserRole> map(List<String> roles) {
        Set<UserRole> userRoles = roles
                .stream()
                .map(userRoleRepository::findByName)
                .collect(Collectors.toSet());
        return Set.copyOf(userRoles);
    }
}
