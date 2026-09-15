package com.school.StudentManagement.controller;

import com.school.StudentManagement.model.User;
import com.school.StudentManagement.service.*;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired private StudentService studentService;
    @Autowired private ClubService clubService;
    @Autowired private EventService eventService;
    @Autowired private MembershipService membershipService;
    @Autowired private AttendanceService attendanceService;
    @Autowired private UserService userService;
    @Autowired private AnnouncementService announcementService;

    @GetMapping("/dashboard")
    public String dashboard(HttpSession session, Model model) {
        if (!isAdmin(session)) return "redirect:/login";

        model.addAttribute("totalStudents", studentService.getAllStudents().size());
        model.addAttribute("totalClubs", clubService.getAllClubs().size());
        model.addAttribute("totalEvents", eventService.getAllEvents().size());
        model.addAttribute("totalMemberships", membershipService.getAllMemberships().size());
        model.addAttribute("recentAnnouncements", announcementService.getAllAnnouncements());
        model.addAttribute("clubs", clubService.getAllClubs());
        return "admin/dashboard";
    }

    @GetMapping("/users")
    public String manageUsers(HttpSession session, Model model) {
        if (!isAdmin(session)) return "redirect:/login";
        model.addAttribute("users", userService.getAllUsers());
        model.addAttribute("clubs", clubService.getAllClubs());
        return "admin/users";
    }

    @PostMapping("/users/save")
    public String saveUser(@ModelAttribute User user,
                           RedirectAttributes redirectAttributes,
                           HttpSession session) {
        if (!isAdmin(session)) return "redirect:/login";
        userService.saveUser(user);
        redirectAttributes.addFlashAttribute("success", "User saved successfully.");
        return "redirect:/admin/users";
    }

    @GetMapping("/users/delete/{id}")
    public String deleteUser(@PathVariable Long id,
                             RedirectAttributes redirectAttributes,
                             HttpSession session) {
        if (!isAdmin(session)) return "redirect:/login";
        userService.deleteUser(id);
        redirectAttributes.addFlashAttribute("success", "User deleted successfully.");
        return "redirect:/admin/users";
    }

    private boolean isAdmin(HttpSession session) {
        return "ADMIN".equals(session.getAttribute("role"));
    }
}