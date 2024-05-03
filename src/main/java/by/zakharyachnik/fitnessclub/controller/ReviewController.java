package by.zakharyachnik.fitnessclub.controller;


import by.zakharyachnik.fitnessclub.entity.User;
import by.zakharyachnik.fitnessclub.exceptions.NotFoundException;
import by.zakharyachnik.fitnessclub.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/reviews")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    @GetMapping
    public String showReviews(
            Authentication authentication,
            Model model
    ) {
        model.addAttribute("username", authentication.getName());
        model.addAttribute("reviews", reviewService.findAll());
        return "reviews";
    }

    @PostMapping
    public String addReview(
            @RequestParam("reviewText") String reviewText,
            @RequestParam("username") String username
    ) {
        try {
            reviewService.save(reviewText, username);
            return "redirect:/reviews";
        } catch (NotFoundException e) {
            return "redirect:/reviews?error=true";
        }
    }
}
