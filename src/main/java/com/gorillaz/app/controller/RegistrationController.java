package com.gorillaz.app.controller;

import com.gorillaz.app.domain.event.Event;
import com.gorillaz.app.domain.event.Registration;
import com.gorillaz.app.domain.event.RegistrationDTO;
import com.gorillaz.app.service.EventService;
import com.gorillaz.app.service.RegistrationService;
import com.gorillaz.app.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/registration")
public class RegistrationController {

    @Autowired
    private UserService userService;

    @Autowired
    private EventService eventService;

    @Autowired
    private RegistrationService registrationService;

    @PostMapping
    public ResponseEntity<String> register(@RequestBody RegistrationDTO data) {

        if (data.eventId() == null) {
            return ResponseEntity.badRequest().build();
        }

        var user = userService.getAuthenticatedUser();

        if (user == null) {
            return ResponseEntity.badRequest().build();
        }

        Optional<Event> event = eventService.findEventById(data.eventId().getId());

        if (event.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        Event eventId = event.get();

        var isUserAlreadyRegistered = registrationService.isUserAlreadyRegisteredToToEvent(user, event.get());

        if (isUserAlreadyRegistered) {
            return ResponseEntity.badRequest().body("O usuário já se inscreveu para este evento");
        }

        Registration registration =  new Registration(user, eventId);

        Registration register = registrationService.save(registration);

        if (register.getId() == null) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok().build();
    }
}
