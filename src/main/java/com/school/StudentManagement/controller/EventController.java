package com.school.StudentManagement.controller;

import com.school.StudentManagement.model.Event;
import com.school.StudentManagement.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/events")
public class EventController {

    @Autowired
    private EventService eventService;

    @GetMapping
    public String listEvents(Model model) {
        model.addAttribute("events", eventService.getAllEvents());
        return "events/list";
    }

    @GetMapping("/new")
    public String newEventForm(Model model) {
        model.addAttribute("event", new Event());
        return "events/form";
    }

    @PostMapping("/save")
    public String saveEvent(@ModelAttribute Event event) {
        eventService.saveEvent(event);
        return "redirect:/events";
    }

    @GetMapping("/edit/{id}")
    public String editEvent(@PathVariable Long id, Model model) {
        model.addAttribute("event", eventService.getEventById(id));
        return "events/form";
    }

    @GetMapping("/delete/{id}")
    public String deleteEvent(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            eventService.deleteEvent(id);
            redirectAttributes.addFlashAttribute("success", "Event deleted successfully.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Cannot delete this event as it has linked records.");
        }
        return "redirect:/events";
    }
}