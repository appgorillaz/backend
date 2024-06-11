package com.gorillaz.app.controller;

import com.gorillaz.app.domain.event.Event;
import com.gorillaz.app.domain.event.NewEventDTO;
import com.gorillaz.app.service.EventService;
import com.gorillaz.app.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/events")
public class EventController {
    @Autowired
    private EventService eventService;

    @Autowired
    private UserService userService;

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
