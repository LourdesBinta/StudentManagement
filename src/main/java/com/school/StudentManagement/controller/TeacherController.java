package com.school.StudentManagement.controller;

import com.school.StudentManagement.model.Announcement;
import com.school.StudentManagement.model.Attendance;
import com.school.StudentManagement.model.Student;
import com.school.StudentManagement.service.*;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/teacher")
public class TeacherController {

    @Autowired private ClubService clubService;
    @Autowired private MembershipService membershipService;
    @Autowired private StudentService studentService;
    @Autowired private AttendanceService attendanceService;
    @Autowired private EventService eventService;
    @Autowired private AnnouncementService announcementService;

    @GetMapping("/dashboard")
    public String dashboard(HttpSession session, Model model) {
        if (!isTeacher(session)) return "redirect:/login";

        Integer clubId = (Integer) session.getAttribute("clubId");
        String username = (String) session.getAttribute("username");

        model.addAttribute("clubs", clubService.getAllClubs());
        model.addAttribute("allClubs", clubService.getAllClubs());

        if (clubId != null) {
            // Get club details
            model.addAttribute("myClub", clubService.getClubById(clubId.longValue()));

            // Get members
            List memberships = membershipService.getAllMemberships().stream()
                    .filter(m -> m.getClubId() != null && m.getClubId().equals(clubId))
                    .collect(Collectors.toList());
            model.addAttribute("memberships", memberships);

            // Student map
            Map<Integer, Student> studentMap = studentService.getAllStudents().stream()
                    .collect(Collectors.toMap(
                            s -> s.getId().intValue(), s -> s, (a, b) -> a));
            model.addAttribute("studentMap", studentMap);

            // Announcements for this club
            model.addAttribute("announcements",
                    announcementService.getAnnouncementsByClub(clubId));

            // Events for this club
            model.addAttribute("events", eventService.getAllEvents().stream()
                    .filter(e -> e.getClubId() != null && e.getClubId().equals(clubId))
                    .collect(Collectors.toList()));
        }

        model.addAttribute("noClub", clubId == null);
        return "teacher/dashboard";
    }

    @PostMapping("/announcement/save")
    public String saveAnnouncement(@RequestParam String title,
                                   @RequestParam String message,
                                   HttpSession session,
                                   RedirectAttributes redirectAttributes) {
        if (!isTeacher(session)) return "redirect:/login";

        Integer clubId = (Integer) session.getAttribute("clubId");
        String username = (String) session.getAttribute("username");

        if (clubId == null) {
            redirectAttributes.addFlashAttribute("error",
                    "You are not assigned to a club yet.");
            return "redirect:/teacher/dashboard";
        }

        Announcement announcement = new Announcement();
        announcement.setClubId(clubId);
        announcement.setTitle(title);
        announcement.setMessage(message);
        announcement.setPostedBy(username);
        announcement.setPostedDate(LocalDate.now().toString());
        announcementService.saveAnnouncement(announcement);

        redirectAttributes.addFlashAttribute("success",
                "Announcement posted successfully!");
        return "redirect:/teacher/dashboard";
    }

    @GetMapping("/announcement/delete/{id}")
    public String deleteAnnouncement(@PathVariable Long id,
                                     HttpSession session,
                                     RedirectAttributes redirectAttributes) {
        if (!isTeacher(session)) return "redirect:/login";
        announcementService.deleteAnnouncement(id);
        redirectAttributes.addFlashAttribute("success", "Announcement deleted.");
        return "redirect:/teacher/dashboard";
    }

    @GetMapping("/attendance")
    public String attendanceForm(HttpSession session, Model model) {
        if (!isTeacher(session)) return "redirect:/login";
        Integer clubId = (Integer) session.getAttribute("clubId");

        model.addAttribute("events", eventService.getAllEvents().stream()
                .filter(e -> e.getClubId() != null && e.getClubId().equals(clubId))
                .collect(Collectors.toList()));

        List memberships = membershipService.getAllMemberships().stream()
                .filter(m -> m.getClubId() != null && m.getClubId().equals(clubId))
                .collect(Collectors.toList());

        Map<Integer, Student> studentMap = studentService.getAllStudents().stream()
                .collect(Collectors.toMap(
                        s -> s.getId().intValue(), s -> s, (a, b) -> a));

        model.addAttribute("memberships", memberships);
        model.addAttribute("studentMap", studentMap);
        model.addAttribute("attendance", new Attendance());
        return "teacher/attendance";
    }

    @PostMapping("/attendance/save")
    public String saveAttendance(@RequestParam Integer studentId,
                                 @RequestParam Long eventId,
                                 @RequestParam String attendanceStatus,
                                 HttpSession session,
                                 RedirectAttributes redirectAttributes) {
        if (!isTeacher(session)) return "redirect:/login";

        Attendance attendance = new Attendance();
        attendance.setStudentId(studentId);
        attendance.setEventId(eventId.intValue());
        attendance.setAttendanceStatus(attendanceStatus);
        attendanceService.saveAttendance(attendance);

        redirectAttributes.addFlashAttribute("success", "Attendance saved!");
        return "redirect:/teacher/attendance";
    }

    private boolean isTeacher(HttpSession session) {
        return "TEACHER".equals(session.getAttribute("role"));
    }
}