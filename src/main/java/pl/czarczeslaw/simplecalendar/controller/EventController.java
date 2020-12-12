package pl.czarczeslaw.simplecalendar.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pl.czarczeslaw.simplecalendar.mapper.EventMapper;
import pl.czarczeslaw.simplecalendar.model.Event;
import pl.czarczeslaw.simplecalendar.model.Priority;
import pl.czarczeslaw.simplecalendar.model.dto.EventDto;
import pl.czarczeslaw.simplecalendar.model.dto.EventEditDto;
import pl.czarczeslaw.simplecalendar.service.EventService;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Arrays;
import java.util.Optional;

@Controller
@RequestMapping(path = "/event")
@AllArgsConstructor
public class EventController {
    private final EventService eventService;
    private final EventMapper eventMapper;

    @GetMapping(path = "/get/all")
    public String getAll(Model model) {
        model.addAttribute("events", eventService.getAll());
        model.addAttribute("title", "All events");
        return "/events/all";
    }

    @GetMapping(path = "/get/today")
    public String getToday(Model model) {
        model.addAttribute("events", eventService.getToday());
        model.addAttribute("title", "Today Events");
        return "/events/all";
    }

    @GetMapping(path = "/get/past")
    public String getPast(Model model) {
        model.addAttribute("events", eventService.getPast());
        model.addAttribute("title", "Events from past");
        return "/events/all";
    }

    @GetMapping(path = "/get/{id}")
    public String getById(@PathVariable Long id, Model model) {
        model.addAttribute("event", eventService.findById(id));
        return "/events/event";
    }

    @GetMapping(path = "/get/name/{name}")
    public String getListNames(@PathVariable String name, Model model) {
        if (name == null || name.isBlank()) {
            model.addAttribute("events", eventService.getAll());
        } else {
            model.addAttribute("events", eventService.getListByName(name));
        }
        return "/events/all";
    }

    @GetMapping(path = "/new")
    public String createEvent(EventDto dto, Model model) {
        model.addAttribute("dto", dto);
        return "/events/new";
    }

    @PostMapping(path = "/new")
    public String createEvent(@Valid @ModelAttribute("dto") EventDto dto, BindingResult bindingResult) throws Exception {
        if (bindingResult.hasErrors()) {
            return "/events/new";
        } else {
            eventService.saveEvent(dto);
            return "redirect:/event/get/all";
        }

    }

    @GetMapping(path = "/edit/{id}")
    public String editEvent(@PathVariable Long id, Model model, EventEditDto dto) {
        Event event = eventService.findById(id);
        dto = eventMapper.getEditDtoFromEvent(event);
        model.addAttribute("dto", dto);
        return "/events/edit";
    }

    @PostMapping(path = "/edit")
    public String editEvent(@Valid @ModelAttribute("dto") EventEditDto dto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "redirect:/event/get/all";
        } else {
            Event event = eventService.editEvent(dto);
            return "redirect:/event/get/" + event.getId();
        }
    }

    @GetMapping(path = "delete/{id}")
    public String deleteEvent(@PathVariable Long id) {
        eventService.deleteEvent(id);
        return "redirect:/event/get/all";
    }
}
