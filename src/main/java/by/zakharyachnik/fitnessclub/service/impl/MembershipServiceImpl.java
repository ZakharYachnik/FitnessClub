package by.zakharyachnik.fitnessclub.service.impl;

import by.zakharyachnik.fitnessclub.dto.MembershipDto;
import by.zakharyachnik.fitnessclub.dto.UserMembershipDto;
import by.zakharyachnik.fitnessclub.entity.MembershipsStatistic;
import by.zakharyachnik.fitnessclub.entity.UserMembership;
import by.zakharyachnik.fitnessclub.mapper.MembershipMapper;
import by.zakharyachnik.fitnessclub.repository.MembershipRepository;
import by.zakharyachnik.fitnessclub.repository.UserMembershipRepository;
import by.zakharyachnik.fitnessclub.service.MembershipService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MembershipServiceImpl implements MembershipService {

    private final MembershipRepository membershipRepository;

    private final MembershipMapper membershipMapper;

    private final UserMembershipRepository userMembershipRepository;

    @Override
    public List<MembershipDto> findAll() {
        return membershipRepository.findAll()
                .stream()
                .map(membershipMapper::toMembershipDto)
                .toList();
    }

    @Override
    public MembershipsStatistic getMembershipStatistics() {
        List<UserMembership> userMemberships = userMembershipRepository.findByActive(true);

        MembershipsStatistic membershipsStatistic = new MembershipsStatistic()
                .setActiveCount(userMemberships.size())
                .setTotalAmount(userMemberships
                        .stream()
                        .mapToDouble(userMembership -> userMembership.getMembership().getPrice())
                        .sum());

        Map<Integer, Long> durationCounts = userMemberships.stream()
                .map(userMembership -> userMembership.getMembership().getDuration())
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

        Optional<Map.Entry<Integer, Long>> mostCommonDurationEntry = durationCounts.entrySet().stream()
                .max(Map.Entry.comparingByValue());

        mostCommonDurationEntry.ifPresent(integerLongEntry -> membershipsStatistic.setMostCommonDuration(integerLongEntry.getKey())
                .setMostCommonDurationCount(integerLongEntry.getValue()));

        Optional<Map.Entry<Integer, Long>> rarestDurationEntry = durationCounts.entrySet().stream()
                .min(Map.Entry.comparingByValue());

        rarestDurationEntry.ifPresent(integerLongEntry -> membershipsStatistic.setRarestDuration(integerLongEntry.getKey())
                .setRarestDurationCount(integerLongEntry.getValue()));

        return membershipsStatistic;
    }

}
