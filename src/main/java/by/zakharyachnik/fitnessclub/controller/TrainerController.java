package by.zakharyachnik.fitnessclub.controller;

import by.zakharyachnik.fitnessclub.entity.PersonalTraining;
import by.zakharyachnik.fitnessclub.exceptions.AlreadyExistsException;
import by.zakharyachnik.fitnessclub.exceptions.NotFoundException;
import by.zakharyachnik.fitnessclub.service.PersonalTrainingService;
import by.zakharyachnik.fitnessclub.service.TrainerService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class TrainerController {

    private final TrainerService trainerService;

    private final PersonalTrainingService personalTrainingService;

    @RequestMapping("/trainers")
    public String showTrainers(
            Model model
    ) {
        model.addAttribute("trainers", trainerService.findAll());
        return "trainers";
    }

    @PostMapping("/trainers/sign-up")
    public String signUpForClasses(
            Authentication authentication,
            @RequestParam("trainerId") Long trainerId
    ) {
        try {
            personalTrainingService.addNewPersonalTraining(trainerId, authentication.getName());
            return "redirect:/trainers?success=true";
        } catch (NotFoundException e) {
            return "redirect:/trainers?notFoundError=true";
        } catch (AlreadyExistsException e) {
            return "redirect:/trainers?alreadyExistsError=true";
        }
    }
}
