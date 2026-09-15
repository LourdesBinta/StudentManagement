package com.school.StudentManagement.controller;

import com.school.StudentManagement.model.Club;
import com.school.StudentManagement.model.Event;
import com.school.StudentManagement.model.Membership;
import com.school.StudentManagement.model.Student;
import com.school.StudentManagement.service.ClubService;
import com.school.StudentManagement.service.EventService;
import com.school.StudentManagement.service.MembershipService;
import com.school.StudentManagement.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/clubs")
public class ClubController {

    @Autowired
    private ClubService clubService;

    @Autowired
    private MembershipService membershipService;

    @Autowired
    private StudentService studentService;

    @Autowired
    private EventService eventService;

    @GetMapping
    public String listClubs(Model model) {
        model.addAttribute("clubs", clubService.getAllClubs());
        return "clubs/list";
    }

    @GetMapping("/{id}/members")
    public String viewMembers(@PathVariable Long id, Model model) {
        Club club = clubService.getClubById(id);

        List<Membership> memberships = membershipService.getAllMemberships().stream()
                .filter(m -> m.getClubId() != null && m.getClubId().equals(id.intValue()))
                .collect(Collectors.toList());

        Map<Integer, Student> studentMap = studentService.getAllStudents().stream()
                .collect(Collectors.toMap(
                        s -> s.getId().intValue(),
                        s -> s,
                        (a, b) -> a
                ));

        model.addAttribute("club", club);
        model.addAttribute("memberships", memberships);
        model.addAttribute("studentMap", studentMap);
        return "clubs/members";
    }

    @GetMapping("/new")
    public String newClubForm(Model model) {
        model.addAttribute("club", new Club());
        return "clubs/form";
    }

    @PostMapping("/save")
    public String saveClub(@ModelAttribute Club club) {
        clubService.saveClub(club);
        return "redirect:/clubs";
    }

    @GetMapping("/edit/{id}")
    public String editClub(@PathVariable Long id, Model model) {
        model.addAttribute("club", clubService.getClubById(id));
        return "clubs/form";
    }

    @GetMapping("/delete/{id}")
    public String deleteClub(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        long membershipCount = membershipService.getAllMemberships().stream()
                .filter(m -> m.getClubId() != null && m.getClubId().equals(id.intValue()))
                .count();

        long eventCount = eventService.getAllEvents().stream()
                .filter(e -> e.getClubId() != null && e.getClubId().equals(id.intValue()))
                .count();

        if (membershipCount > 0 || eventCount > 0) {
            StringBuilder message = new StringBuilder("Cannot delete this club — it still has ");
            if (membershipCount > 0) {
                message.append(membershipCount).append(membershipCount == 1 ? " membership record" : " membership records");
            }
            if (membershipCount > 0 && eventCount > 0) {
                message.append(" and ");
            }
            if (eventCount > 0) {
                message.append(eventCount).append(eventCount == 1 ? " event" : " events");
            }
            message.append(" linked to it. Remove those first, or archive the club instead of deleting it.");

            redirectAttributes.addFlashAttribute("error", message.toString());
            return "redirect:/clubs";
        }

        try {
            clubService.deleteClub(id);
            redirectAttributes.addFlashAttribute("success", "Club deleted successfully.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Could not delete this club due to an unexpected database error.");
        }
        return "redirect:/clubs";
    }
}