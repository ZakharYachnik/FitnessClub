package by.zakharyachnik.fitnessclub.controller;

import by.zakharyachnik.fitnessclub.entity.User;
import by.zakharyachnik.fitnessclub.exceptions.AlreadyExistsException;
import by.zakharyachnik.fitnessclub.exceptions.NotFoundException;
import by.zakharyachnik.fitnessclub.mapper.UserMapper;
import by.zakharyachnik.fitnessclub.service.PersonalTrainingService;
import by.zakharyachnik.fitnessclub.service.UserMembershipService;
import by.zakharyachnik.fitnessclub.service.UserService;
import by.zakharyachnik.fitnessclub.validation.UserDataValidation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@PreAuthorize("hasRole('ROLE_USER')")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    private final UserMapper userMapper;

    private final UserDataValidation userDataValidation;

    private final UserMembershipService userMembershipService;

    private final PersonalTrainingService personalTrainingService;

    @GetMapping("/profile")
    public String showProfile(
            @AuthenticationPrincipal User user,
            Model model
    ) {
        model.addAttribute("user", userService.findById(user.getId()));
        return "user_profile";
    }

    @GetMapping("/profile/edit")
    public String editProfile(
            @AuthenticationPrincipal User user,
            Model model
    ) {
        model.addAttribute("user", userService.findById(user.getId()));
        return "edit_profile";
    }

    @PostMapping("/profile/edit")
    public String updateProfile(
            @RequestParam("username") String username,
            @RequestParam("password") String password,
            @RequestParam("phone_number") String phoneNumber,
            @RequestParam("full_name") String fullName,
            @RequestParam("userId") Long userId
    ) {
        if(!userDataValidation.checkRegistrationData(username, password, fullName, phoneNumber)){
            return "redirect:/profile/edit?dataError=true";
        }

        try {
            userService.update(username, fullName, password, phoneNumber, userId);
            return "redirect:/profile";
        } catch (AlreadyExistsException e) {
            return "redirect:/profile/edit?alreadyExistsError=true";
        } catch (NotFoundException e) {
            return "redirect:/profile/edit?dataError=true";
        }
    }

    @GetMapping("profile/user-memberships")
    public String showUserMemberships(
            @AuthenticationPrincipal User user,
            Model model
    ) {
        try {
            model.addAttribute("userMembership", userMembershipService.findUserMembershipByUserId(user.getId()));
            return "user_memberships";
        } catch (NotFoundException e) {
            model.addAttribute("notFoundError", "У вас нет активных абонементов");
            return "user_memberships";
        }
    }

    @GetMapping("/profile/user-trainers")
    public String showUserTrainers(
            @AuthenticationPrincipal User user,
            Model model
    ) {
        try {
            model.addAttribute("trainers", personalTrainingService.getUserTrainers(user.getId()));
            model.addAttribute("customerId", user.getId());
        } catch (NotFoundException e) {
            model.addAttribute("notFoundError", "Вы не записаны ни к одному тренеру");
        }
        return "user_trainers";
    }

    @PostMapping("/profile/user-trainers/cancel")
    public String cancelUserPersonalTraining(
            @RequestParam("trainerId") Long trainerId,
            @RequestParam("customerId") Long customerId
    ){
        try {
            personalTrainingService.cancelPersonalTraining(trainerId, customerId);
            return "redirect:/profile/user-trainers?success=true";
        } catch (NotFoundException e) {
            return "redirect:/profile/user-trainers?notFoundError=true";
        }
    }
}
