package by.zakharyachnik.fitnessclub.controller;

import by.zakharyachnik.fitnessclub.entity.User;
import by.zakharyachnik.fitnessclub.exceptions.NotFoundException;
import by.zakharyachnik.fitnessclub.service.PersonalTrainingService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/personal-trainings")
@PreAuthorize("hasRole('TRAINER')")
@RequiredArgsConstructor
public class PersonalTrainingController {

    private final PersonalTrainingService personalTrainingService;

    @PostMapping("/cancel")
    public String cancelPersonalTraining(
            @RequestParam("personalTrainingId") Long personalTrainingId
    ) {
        try {
            personalTrainingService.cancelPersonalTraining(personalTrainingId);
            return "redirect:/personal-trainings?success=true";
        } catch (NotFoundException e) {
            return "redirect:/personal-trainings?notFoundError=true";
        }
    }

    @GetMapping
    public String showPersonalTrainings(
            @AuthenticationPrincipal User trainer,
            Model model
    ) {
        model.addAttribute("personalTrainings", personalTrainingService.getTrainerPersonalTrainings(trainer.getId()));
        model.addAttribute("trainerId", trainer.getId());
        return "personal_trainings";
    }

    @GetMapping("{id}")
    public String showPersonalTraining(
            @PathVariable("id") Long personalTrainingId,
            Model model
    ) {
        model.addAttribute("personalTraining", personalTrainingService.getPersonalTraining(personalTrainingId));
        return "personal_training";
    }

    @PostMapping("{id}")
    public String addTrainingProgramToPersonalTraining(
            @PathVariable("id") Long personalTrainingId,
            @RequestParam("trainingProgramId") Long trainingProgramId
    ){
        try {
            personalTrainingService.addTrainingProgramToPersonalTraining(personalTrainingId, trainingProgramId);
            return "redirect:/personal-trainings/" + personalTrainingId + "?success=true";
        } catch (NotFoundException e) {
            return "redirect:/personal-trainings/" + personalTrainingId + "?notFoundError=true";
        }
    }
}
