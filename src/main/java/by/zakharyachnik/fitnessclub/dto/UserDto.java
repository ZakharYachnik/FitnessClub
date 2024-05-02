package by.zakharyachnik.fitnessclub.dto;

import java.util.List;

public record UserDto(
    Long id,
    String username,
    String fullName,
    boolean active,
    String phoneNumber,
    List<String> userRoles
) {
}
