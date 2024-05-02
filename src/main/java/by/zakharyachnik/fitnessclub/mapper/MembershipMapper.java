package by.zakharyachnik.fitnessclub.mapper;

import by.zakharyachnik.fitnessclub.dto.MembershipDto;
import by.zakharyachnik.fitnessclub.entity.Membership;
import org.mapstruct.Mapper;

@Mapper
public interface MembershipMapper {

    MembershipDto toMembershipDto(Membership membership);

    Membership toMembership(MembershipDto membershipDto);
}
