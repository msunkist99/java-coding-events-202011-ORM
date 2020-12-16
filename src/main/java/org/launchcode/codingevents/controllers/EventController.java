package org.launchcode.codingevents.controllers;

import org.launchcode.codingevents.data.EventCategoryRepository;
import org.launchcode.codingevents.data.EventRepository;
import org.launchcode.codingevents.data.TagRepository;
import org.launchcode.codingevents.models.Event;
import org.launchcode.codingevents.models.EventCategory;
import org.launchcode.codingevents.models.Tag;
import org.launchcode.codingevents.models.dto.EventTagDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

/**
 * Created by Chris Bay
 */
@Controller
@RequestMapping("events")
public class EventController {

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private EventCategoryRepository eventCategoryRepository;

    @Autowired
    private TagRepository tagRepository;

    @GetMapping
    public String displayEvents(@RequestParam(required = false) Integer categoryId,
                                Model model) {
        if (categoryId == null){
            model.addAttribute("title", "All Events");
            model.addAttribute("events", eventRepository.findAll());
        }
        else {
            Optional<EventCategory> results = eventCategoryRepository.findById(categoryId);
            if (results.isEmpty()){
                model.addAttribute("title", "Invalid Category ID: " + categoryId);
            }
            else {
                EventCategory category = results.get();
                model.addAttribute("title", category.getName());
                model.addAttribute("events", category.getEvents());
            }
        }

        return "events/index";
    }

    @GetMapping("create")
    public String displayCreateEventForm(Model model) {
        model.addAttribute("title", "Create Event");
        model.addAttribute(new Event());
        model.addAttribute("eventCategories", eventCategoryRepository.findAll());
        return "events/create";
    }

    @PostMapping("create")
    public String processCreateEventForm(@ModelAttribute @Valid Event newEvent,
                                         Errors errors, Model model) {
        if(errors.hasErrors()) {
            model.addAttribute("title", "Create Event");
            model.addAttribute("eventCategories", eventCategoryRepository.findAll());
            return "events/create";
        }

        eventRepository.save(newEvent);
        return "redirect:";
    }

    @GetMapping("delete")
    public String displayDeleteEventForm(Model model) {
        model.addAttribute("title", "Delete Events");
        model.addAttribute("events", eventRepository.findAll());
        return "events/delete";
    }

    @PostMapping("delete")
    public String processDeleteEventsForm(@RequestParam(required = false) int[] eventIds) {

        if (eventIds != null) {
            for (int id : eventIds) {
                eventRepository.deleteById(id);
            }
        }

        return "redirect:";
    }

    @GetMapping("add-tag")      // handles get requests for events/add-tag@eventId=13
    public String DisplayAddTagForm(@RequestParam Integer eventId,
                                    Model model){
        Optional<Event> result = eventRepository.findById(eventId);

        if (!result.isEmpty()) {
            Event event = result.get();

            model.addAttribute("title", "Add Tag to: " + event.getName());
            model.addAttribute("tags", tagRepository.findAll());

            EventTagDTO eventTagDTO = new EventTagDTO();
            eventTagDTO.setEvent(event);
            model.addAttribute("eventTagDTO", eventTagDTO);
            return "events/add-tag";
        }
        else {
            // there should be some error handling here if the event for eventId is not found
            return "events/add-tag";
        }
    }

    @PostMapping("add-tag")
    public String ProcessAddTagForm(@ModelAttribute @Valid EventTagDTO eventTagDTO,
                                    Errors errors,
                                    Model model ){

        if (!errors.hasErrors()) {
            Event event = eventTagDTO.getEvent();
            Tag tag = eventTagDTO.getTag();

            if (!event.getTags().contains(tag)) {
                event.addTag(tag);
                eventRepository.save(event);
            }
            return "redirect:detail?eventId=" + event.getId();
        }
        else {
            // this needs better error handling
            return "redirect:detail";
        }
    }

}
