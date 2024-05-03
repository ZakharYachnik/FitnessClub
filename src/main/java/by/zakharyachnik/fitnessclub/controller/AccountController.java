package by.zakharyachnik.fitnessclub.controller;

import by.zakharyachnik.fitnessclub.dto.UserDto;
import by.zakharyachnik.fitnessclub.exceptions.AlreadyExistsException;
import by.zakharyachnik.fitnessclub.service.UserService;
import by.zakharyachnik.fitnessclub.validation.UserDataValidation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class AccountController {

    private final UserDataValidation userDataValidation;

    private final UserService userService;

    @GetMapping("/registration")
    public String showRegistrationPage(
    ) {
        return "registration";
    }

    @PostMapping("/registration")
    public String registerUser(
            @RequestParam("username") String username,
            @RequestParam("password") String password,
            @RequestParam("phone_number") String phoneNumber,
            @RequestParam("full_name") String fullName,
            Model model
    ) {
        if(!userDataValidation.checkRegistrationData(username, password, fullName, phoneNumber)){
            return "redirect:/registration?dataError=true";
        }

        String role = "ROLE_USER";
        try {
            userService.save(username, fullName, password, phoneNumber, role);
            return "redirect:/login";
        } catch (AlreadyExistsException e) {
            return "redirect:/registration?alreadyExistsError=true";
        }
    }

    @GetMapping("/main-page")
    public String showMainPage(
            Authentication authentication
    ) {
        System.out.println(authentication.getAuthorities());
        return "main_page";
    }



}
