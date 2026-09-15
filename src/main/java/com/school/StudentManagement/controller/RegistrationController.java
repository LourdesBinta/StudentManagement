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

import java.time.LocalDate;

@Controller
@RequestMapping("/register")
public class RegistrationController {

    @Autowired
    private StudentService studentService;

    @Autowired
    private ClubService clubService;

    @Autowired
    private MembershipService membershipService;

    // Step 1 — Show search form
    @GetMapping
    public String showRegistration(Model model) {
        model.addAttribute("clubs", clubService.getAllClubs());
        return "register";
    }

    // Step 2 — Search for student by name
    @PostMapping("/search")
    public String searchStudent(@RequestParam String firstName,
                                @RequestParam String lastName,
                                @RequestParam Long clubId,
                                Model model) {
        Student student = studentService.findByName(firstName, lastName);
        model.addAttribute("clubs", clubService.getAllClubs());
        model.addAttribute("selectedClubId", clubId);
        model.addAttribute("firstName", firstName);
        model.addAttribute("lastName", lastName);

        if (student != null) {
            model.addAttribute("student", student);
            model.addAttribute("message", "Student found! Review details and confirm registration.");
        } else {
            model.addAttribute("notFound", true);
            model.addAttribute("newStudent", new Student());
            model.addAttribute("message", "Student not found. Please complete the registration form below.");
        }
        return "register";
    }

    // Step 3a — Register existing student to club
    @PostMapping("/existing")
    public String registerExisting(@RequestParam Long studentId,
                                   @RequestParam Long clubId,
                                   @RequestParam String schoolYear,
                                   RedirectAttributes redirectAttributes) {
        Membership membership = new Membership();
        membership.setStudentId(Math.toIntExact(studentId));
        membership.setClubId(Math.toIntExact(clubId));
        membership.setDateJoined(LocalDate.now().toString());
        membership.setStatus("Active");
        membership.setSchoolYear(schoolYear);
        membershipService.saveMembership(membership);

        redirectAttributes.addFlashAttribute("success",
                "Student successfully registered to club!");
        return "redirect:/register";
    }

    // Step 3b — Register new student then add to club
    @PostMapping("/new")
    public String registerNew(@ModelAttribute Student student,
                              @RequestParam Long clubId,
                              @RequestParam String schoolYear,
                              RedirectAttributes redirectAttributes) {
        // Save new student
        student.setStudentStatus("Active");
        Student saved = studentService.saveStudent(student);

        // Create membership
        Membership membership = new Membership();
        membership.setStudentId(Math.toIntExact(saved.getId()));
        membership.setClubId(Math.toIntExact(clubId));
        membership.setDateJoined(LocalDate.now().toString());
        membership.setStatus("Active");
        membership.setSchoolYear(schoolYear);
        membershipService.saveMembership(membership);

        redirectAttributes.addFlashAttribute("success",
                "New student registered and added to club successfully!");
        return "redirect:/register";
    }
}