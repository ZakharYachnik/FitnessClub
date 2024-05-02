package by.zakharyachnik.fitnessclub.dto;

import java.time.LocalDate;

public record UserMembershipDto(
    Long id,
    UserDto userDto,
    MembershipDto membershipDto,
    LocalDate purchaseDate,
    boolean active
) {
}
