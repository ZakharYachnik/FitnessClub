package by.zakharyachnik.fitnessclub.controller;


import by.zakharyachnik.fitnessclub.entity.User;
import by.zakharyachnik.fitnessclub.exceptions.AlreadyExistsException;
import by.zakharyachnik.fitnessclub.exceptions.NotFoundException;
import by.zakharyachnik.fitnessclub.service.MembershipService;
import by.zakharyachnik.fitnessclub.service.UserMembershipService;
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
@RequestMapping("/memberships")
@RequiredArgsConstructor
public class MembershipController {

    private final UserMembershipService userMembershipService;

    private final MembershipService membershipService;


    @GetMapping
    public String showMemberships(
            Model model
    ) {
        model.addAttribute("memberships", membershipService.findAll());
        return "memberships";
    }

    @PostMapping("purchase-membership")
    @PreAuthorize("hasRole('USER')")
    public String purchaseMembership(
            Authentication authentication,
            @RequestParam("membershipId") Long membershipId
    ) {
        try {
            userMembershipService.purchaseMembership(membershipId, authentication.getName());
            return "redirect:/memberships?success=true";
        } catch (NotFoundException e) {
            return "redirect:/memberships?notFoundError=true";
        } catch (AlreadyExistsException e) {
            return "redirect:/memberships?alreadyExistsError=true";
        }
    }

    @GetMapping("/active")
    @PreAuthorize("hasRole('ADMIN')")
    public String showActiveMemberships(
            Model model
    ) {
        model.addAttribute("active_memberships", userMembershipService.findAllActiveMemberships());
        return "active_memberships";
    }

    @PostMapping("/active/complete-membership")
    @PreAuthorize("hasRole('ADMIN')")
    public String completeMembership(
            @RequestParam("membershipId") Long membershipId
    ) {
        try {
            userMembershipService.completeUserMembership(membershipId);
            return "redirect:/memberships/active?success=true";
        } catch (NotFoundException e) {
            return "redirect:/memberships/active?notFoundError=true";
        }
    }


}
