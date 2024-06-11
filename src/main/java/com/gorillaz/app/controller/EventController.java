package com.gorillaz.app.controller;

import com.gorillaz.app.domain.event.Event;
import com.gorillaz.app.domain.event.EventDTO;
import com.gorillaz.app.domain.event.NewEventDTO;
import com.gorillaz.app.service.EventService;
import com.gorillaz.app.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/events")
public class EventController {
    @Autowired
    private EventService eventService;

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<List<EventDTO>> getEvents() {

        List<Event> events = eventService.getEvents();

        if (events.isEmpty()) {
            return ResponseEntity.ok().build();
        }

       List<EventDTO> eventDto = events.stream()
               .map(event -> new EventDTO(event.getId(), event.getTitle(), event.getStartDate(), event.getEndDate(), event.getLocation(), event.getType()))
               .collect(Collectors.toList());

        return ResponseEntity.ok(eventDto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EventDTO> getEvent(@PathVariable UUID id) {

        Optional<Event> event = eventService.findEventById(id);

        if (event.isEmpty()) {
            return ResponseEntity.ok().build();
        }

        EventDTO eventDto = new EventDTO(event.get().getId(), event.get().getTitle(), event.get().getStartDate(), event.get().getEndDate(), event.get().getLocation(), event.get().getType());

        return ResponseEntity.ok(eventDto);
    }

    @PostMapping
    public ResponseEntity create(@RequestBody @Validated NewEventDTO data) {

        var user = userService.getAuthenticatedUser();

        if (user == null) {
            return ResponseEntity.badRequest().build();
        }

        var currentDate = LocalDateTime.now();

        if (data.startDate().isBefore(currentDate) || data.endDate().isBefore(currentDate)) {
            return ResponseEntity.badRequest().body("A data de ínicio / término deve ser maior ou igual a data atual");
        }

        Event event = new Event(data.eventName(), data.startDate(), data.endDate(), data.location(), data.type(), user);

        var eventCreated = eventService.save(event);

        if (eventCreated == null) {
            ResponseEntity.badRequest().build();
        }

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
