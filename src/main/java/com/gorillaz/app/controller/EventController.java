package com.gorillaz.app.controller;

import com.gorillaz.app.domain.event.Event;
import com.gorillaz.app.domain.event.NewEventDTO;
import com.gorillaz.app.service.AuthService;
import com.gorillaz.app.service.EventService;
import com.gorillaz.app.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/event")
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

        Event event = new Event(data.eventName(), data.startDate(), data.endDate(), data.location(), data.type(), user);

        var eventCreated = eventService.save(event);

        if (eventCreated == null) {
            ResponseEntity.badRequest().build();
        }

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
