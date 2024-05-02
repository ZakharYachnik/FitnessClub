package by.zakharyachnik.fitnessclub.service;

import by.zakharyachnik.fitnessclub.dto.UserMembershipDto;
import by.zakharyachnik.fitnessclub.exceptions.AlreadyExistsException;
import by.zakharyachnik.fitnessclub.exceptions.NotFoundException;

import java.util.List;

public interface UserMembershipService {

    UserMembershipDto purchaseMembership(Long membershipId, String username) throws NotFoundException, AlreadyExistsException;

    List<UserMembershipDto> findAllActiveMemberships();

    UserMembershipDto completeUserMembership(Long userMembershipId) throws NotFoundException;
}
