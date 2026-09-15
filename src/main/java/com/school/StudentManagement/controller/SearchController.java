package com.school.StudentManagement.controller;

import com.school.StudentManagement.model.Attendance;
import com.school.StudentManagement.model.Membership;
import com.school.StudentManagement.model.Student;
import com.school.StudentManagement.service.AttendanceService;
import com.school.StudentManagement.service.MembershipService;
import com.school.StudentManagement.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/search")
public class SearchController {

    @Autowired
    private StudentService studentService;

    @Autowired
    private AttendanceService attendanceService;

    @Autowired
    private MembershipService membershipService;

    @GetMapping
    public String showSearch() {
        return "search";
    }

    @GetMapping("/student")
    public String searchStudent(@RequestParam String name, Model model) {
        // Search by first or last name
        List<Student> students = studentService.getAllStudents().stream()
                .filter(s -> (s.getFirstName() != null &&
                        s.getFirstName().toLowerCase().contains(name.toLowerCase()))
                        || (s.getLastName() != null &&
                        s.getLastName().toLowerCase().contains(name.toLowerCase())))
                .collect(Collectors.toList());

        model.addAttribute("students", students);
        model.addAttribute("searchName", name);

        if (students.size() == 1) {
            Student student = students.get(0);
            // Get attendance for this student
            List<Attendance> attendance = attendanceService.getAllAttendance().stream()
                    .filter(a -> a.getStudentId() != null &&
                            a.getStudentId().equals(student.getId().intValue()))
                    .collect(Collectors.toList());

            // Get memberships for this student
            List<Membership> memberships = membershipService.getAllMemberships().stream()
                    .filter(m -> m.getStudentId() != null &&
                            m.getStudentId().equals(student.getId().intValue()))
                    .collect(Collectors.toList());

            model.addAttribute("selectedStudent", student);
            model.addAttribute("attendanceRecords", attendance);
            model.addAttribute("memberships", memberships);
        }

        return "search";
    }
}