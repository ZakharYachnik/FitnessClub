package by.zakharyachnik.fitnessclub.controller;


import by.zakharyachnik.fitnessclub.entity.User;
import by.zakharyachnik.fitnessclub.exceptions.NotFoundException;
import by.zakharyachnik.fitnessclub.service.TrainingProgramService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@PreAuthorize("hasRole('ROLE_TRAINER')")
@RequestMapping("/training-programs")
@RequiredArgsConstructor
public class TrainingProgramController {

    private final TrainingProgramService trainingProgramService;

    @GetMapping
    public String showTrainingPrograms(
            Authentication authentication,
            Model model
    ) {
        model.addAttribute("trainerUsername", authentication.getName());
        model.addAttribute("trainingPrograms", trainingProgramService.findAll());
        return "training_programs";
    }

    @GetMapping("/add")
    public String showAddTrainingProgram(
    ){
        return "add_training_program";
    }

    @PostMapping
    public String addTrainingProgram(
        @RequestParam("name") String name,
        @RequestParam("description") String description
    ) {
        trainingProgramService.save(name, description);
        return "redirect:/training-programs?success=true";
    }

    @GetMapping("/{id}")
    public String showTrainingProgram(
        @PathVariable("id") Long trainingProgramId,
        Model model
    ) {
        try {
            model.addAttribute("trainingProgram", trainingProgramService.findById(trainingProgramId));
            return "training_program";
        } catch (NotFoundException e) {
            return "redirect:/training-programs?notFoundError=true";
        }
    }

    @PostMapping("/edit")
    public String editTrainingProgram(
        @RequestParam("name") String name,
        @RequestParam("description") String description,
        @RequestParam("trainingProgramId") Long trainingProgramId
    ) {
        try {
            trainingProgramService.update(name, description, trainingProgramId);
            return "redirect:/training-programs/" + trainingProgramId + "?success=true";
        } catch (NotFoundException e) {
            return "redirect:/training-programs/" + trainingProgramId + "?notFoundError=true";
        }

    }
}
