package com.school.StudentManagement.controller;

import com.school.StudentManagement.model.Attendance;
import com.school.StudentManagement.model.Student;
import com.school.StudentManagement.service.AttendanceService;
import com.school.StudentManagement.service.EventService;
import com.school.StudentManagement.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/attendance")
public class AttendanceController {

    @Autowired
    private AttendanceService attendanceService;

    @Autowired
    private StudentService studentService;

    @Autowired
    private EventService eventService;

    @GetMapping
    public String listAttendance(Model model) {
        Map<Integer, Student> studentMap = studentService.getAllStudents().stream()
                .collect(Collectors.toMap(
                        s -> s.getId().intValue(),
                        s -> s,
                        (a, b) -> a
                ));

        model.addAttribute("attendanceList", attendanceService.getAllAttendance());
        model.addAttribute("studentMap", studentMap);
        model.addAttribute("events", eventService.getAllEvents());
        return "attendance/list";
    }

    @GetMapping("/new")
    public String newAttendanceForm(Model model) {
        model.addAttribute("attendance", new Attendance());
        model.addAttribute("students", studentService.getAllStudents());
        model.addAttribute("events", eventService.getAllEvents());
        return "attendance/form";
    }

    @PostMapping("/save")
    public String saveAttendance(@ModelAttribute Attendance attendance) {
        attendanceService.saveAttendance(attendance);
        return "redirect:/attendance";
    }

    @GetMapping("/edit/{id}")
    public String editAttendance(@PathVariable Long id, Model model) {
        model.addAttribute("attendance", attendanceService.getAttendanceById(id));
        model.addAttribute("students", studentService.getAllStudents());
        model.addAttribute("events", eventService.getAllEvents());
        return "attendance/form";
    }

    @GetMapping("/delete/{id}")
    public String deleteAttendance(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            attendanceService.markAsAbsent(id);
            redirectAttributes.addFlashAttribute("success", "Attendance marked as Absent.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Could not update attendance status.");
        }
        return "redirect:/attendance";
    }
}