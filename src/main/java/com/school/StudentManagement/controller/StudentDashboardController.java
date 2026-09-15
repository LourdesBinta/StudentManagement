package com.school.StudentManagement.controller;

import com.school.StudentManagement.model.Announcement;
import com.school.StudentManagement.model.Club;
import com.school.StudentManagement.model.Event;
import com.school.StudentManagement.model.Membership;
import com.school.StudentManagement.service.*;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/student")
public class StudentDashboardController {

    @Autowired private MembershipService membershipService;
    @Autowired private ClubService clubService;
    @Autowired private EventService eventService;
    @Autowired private AnnouncementService announcementService;
    @Autowired private AttendanceService attendanceService;
    @Autowired private StudentService studentService;

    @GetMapping("/dashboard")
    public String dashboard(HttpSession session, Model model) {
        if (!isStudent(session)) return "redirect:/login";

        Integer clubId = (Integer) session.getAttribute("clubId");

        if (clubId != null) {
            // Get their club
            Club club = clubService.getClubById(clubId.longValue());
            model.addAttribute("myClub", club);

            // Get announcements for their club
            List<Announcement> announcements =
                    announcementService.getAnnouncementsByClub(clubId);
            model.addAttribute("announcements", announcements);

            // Get upcoming events for their club
            List<Event> upcomingEvents = eventService.getAllEvents().stream()
                    .filter(e -> e.getClubId() != null && e.getClubId().equals(clubId))
                    .collect(Collectors.toList());
            model.addAttribute("upcomingEvents", upcomingEvents);

            // Get their attendance
            Long userId = (Long) session.getAttribute("userId");
            if (userId != null) {
                List attendance = attendanceService.getAllAttendance().stream()
                        .filter(a -> a.getStudentId() != null &&
                                a.getStudentId().equals(userId.intValue()))
                        .collect(Collectors.toList());
                model.addAttribute("myAttendance", attendance);

                // Map events for attendance display
                model.addAttribute("events", eventService.getAllEvents());
            }
        }

        model.addAttribute("noClub", clubId == null);
        return "student/dashboard";
    }

    private boolean isStudent(HttpSession session) {
        String role = (String) session.getAttribute("role");
        return "STUDENT".equals(role);
    }
}