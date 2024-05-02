package by.zakharyachnik.fitnessclub.controller;

import by.zakharyachnik.fitnessclub.exceptions.AlreadyExistsException;
import by.zakharyachnik.fitnessclub.exceptions.NotFoundException;
import by.zakharyachnik.fitnessclub.service.MembershipService;
import by.zakharyachnik.fitnessclub.service.UserService;
import by.zakharyachnik.fitnessclub.validation.UserDataValidation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/administration")
@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
@RequiredArgsConstructor
public class AdminController {

    private final UserService userService;

    private final MembershipService membershipService;

    private final UserDataValidation userDataValidation;


    @GetMapping
    public String showAdministration(
            Model model
    ) {
        model.addAttribute("users", userService.findAll());
        return "administration";
    }

    @GetMapping("/add-trainer")
    public String showRegisterTrainer(){
        return "register_trainer";
    }

    @PostMapping("/add-trainer")
    public String registerTrainer(
            @RequestParam("username") String username,
            @RequestParam("password") String password,
            @RequestParam("phone_number") String phoneNumber,
            @RequestParam("full_name") String fullName,
            Model model
    ) {
        if(!userDataValidation.checkRegistrationData(username, password, fullName, phoneNumber)){
            model.addAttribute("message", "Некорректные данные");
            return "register_trainer";
        }

        String role = "ROLE_TRAINER";
        try {
            userService.save(username, fullName, password, phoneNumber, role);
            return "redirect:/administration";
        } catch (AlreadyExistsException e) {
            model.addAttribute("message", "Пользователь с таким именем уже существует");
            return "register_trainer";
        }
    }

    @PostMapping("/block-account")
    public String blockAccount(
            @RequestParam("username") String username
    ) {
        try {
            userService.blockUser(username);
            return "redirect:/administration?successBlockMessage=accountBlocked";
        } catch (NotFoundException e) {
            return "redirect:/administration?errorMessage=inputError";
        }
    }

    @PostMapping("/unblock-account")
    public String unblockAccount(
            @RequestParam("username") String username
    ) {
        try {
            userService.unblockUser(username);
            return "redirect:/administration?successUnblockMessage=accountUnblocked";
        } catch (NotFoundException e) {
            return "redirect:/administration?errorMessage=inputError";
        }
    }

    @GetMapping("/statistics-memberships")
    public String showStatisticsMemberships(
            Model model
    ) {
        model.addAttribute("statistics", membershipService.getMembershipStatistics());
        return "memberships_statistics";
    }
}
