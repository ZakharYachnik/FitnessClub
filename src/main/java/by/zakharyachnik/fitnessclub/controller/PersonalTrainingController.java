package by.zakharyachnik.fitnessclub.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/personal-trainings")
@RequiredArgsConstructor
public class PersonalTrainingController {

    @PostMapping("/cancel")
    public String cancelPersonalTraining(
            @RequestParam("personalTrainingId") Long personalTrainingId
    ) {
        // TODO
        return "redirect:/personal-trainings";
    }
}
