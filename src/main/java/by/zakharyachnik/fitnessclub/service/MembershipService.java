package by.zakharyachnik.fitnessclub.service;

import by.zakharyachnik.fitnessclub.dto.MembershipDto;
import by.zakharyachnik.fitnessclub.entity.MembershipsStatistic;

import java.util.List;

public interface MembershipService {
    List<MembershipDto> findAll();

    MembershipsStatistic getMembershipStatistics();
}
