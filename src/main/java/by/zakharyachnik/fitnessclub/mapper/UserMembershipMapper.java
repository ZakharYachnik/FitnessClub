package by.zakharyachnik.fitnessclub.mapper;

import by.zakharyachnik.fitnessclub.dto.UserMembershipDto;
import by.zakharyachnik.fitnessclub.entity.UserMembership;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(uses = {UserMapper.class})
public interface UserMembershipMapper {

    @Mapping(target = "membershipDto", source = "membership")
    @Mapping(target = "userDto", source = "user")
    UserMembershipDto toUserMembershipDto(UserMembership userMembership);

    @Mapping(target = "membership", source = "membershipDto")
    @Mapping(target = "user", source = "userDto")
    UserMembership toUserMembership(UserMembershipDto userMembershipDto);
}
