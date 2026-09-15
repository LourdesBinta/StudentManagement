package com.school.StudentManagement.controller;

import com.school.StudentManagement.model.Membership;
import com.school.StudentManagement.model.Student;
import com.school.StudentManagement.service.ClubService;
import com.school.StudentManagement.service.MembershipService;
import com.school.StudentManagement.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/memberships")
public class MembershipController {

    @Autowired
    private MembershipService membershipService;

    @Autowired
    private StudentService studentService;

    @Autowired
    private ClubService clubService;

    @GetMapping
    public String listMemberships(Model model) {
        // Build student name map
        Map<Integer, Student> studentMap = studentService.getAllStudents().stream()
                .collect(Collectors.toMap(
                        s -> s.getId().intValue(),
                        s -> s,
                        (a, b) -> a
                ));

        model.addAttribute("memberships", membershipService.getAllMemberships());
        model.addAttribute("studentMap", studentMap);
        model.addAttribute("clubs", clubService.getAllClubs());
        return "memberships/list";
    }

    @GetMapping("/new")
    public String newMembershipForm(Model model) {
        model.addAttribute("membership", new Membership());
        model.addAttribute("students", studentService.getAllStudents());
        model.addAttribute("clubs", clubService.getAllClubs());
        return "memberships/form";
    }

    @PostMapping("/save")
    public String saveMembership(@ModelAttribute Membership membership) {
        membershipService.saveMembership(membership);
        return "redirect:/memberships";
    }

    @GetMapping("/edit/{id}")
    public String editMembership(@PathVariable Long id, Model model) {
        model.addAttribute("membership", membershipService.getMembershipById(id));
        model.addAttribute("students", studentService.getAllStudents());
        model.addAttribute("clubs", clubService.getAllClubs());
        return "memberships/form";
    }

    @GetMapping("/delete/{id}")
    public String deleteMembership(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            membershipService.markAsInactive(id);
            redirectAttributes.addFlashAttribute("success", "Membership marked as Inactive.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Could not update membership status.");
        }
        return "redirect:/memberships";
    }
}