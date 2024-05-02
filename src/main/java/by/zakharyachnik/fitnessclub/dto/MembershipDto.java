package by.zakharyachnik.fitnessclub.dto;

public record MembershipDto(
    Long id,
    String name,
    Double price,
    String description,
    Integer duration
) {
}
