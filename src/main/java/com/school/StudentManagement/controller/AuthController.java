package com.school.StudentManagement.controller;

import com.school.StudentManagement.model.User;
import com.school.StudentManagement.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class AuthController {

    @Autowired
    private UserService userService;

    @GetMapping("/")
    public String root() {
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @PostMapping("/login")
    public String login(@RequestParam String username,
                        @RequestParam String password,
                        HttpSession session,
                        Model model) {
        User user = userService.findByUsername(username);
        if (user != null && user.getPassword().equals(password)) {
            session.setAttribute("loggedIn", true);
            session.setAttribute("username", username);
            session.setAttribute("role", user.getRole());
            session.setAttribute("clubId", user.getClubId());
            session.setAttribute("userId", user.getId());

            // Redirect based on role
            if ("ADMIN".equals(user.getRole())) {
                return "redirect:/admin/dashboard";
            } else if ("TEACHER".equals(user.getRole())) {
                return "redirect:/teacher/dashboard";
            } else {
                return "redirect:/student/dashboard";
            }
        } else {
            model.addAttribute("error", "Invalid username or password.");
            return "login";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }
}