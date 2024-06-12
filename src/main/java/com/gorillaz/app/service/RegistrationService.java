package com.gorillaz.app.service;

import com.gorillaz.app.domain.event.Event;
import com.gorillaz.app.domain.event.Registration;
import com.gorillaz.app.domain.user.User;
import com.gorillaz.app.repository.RegistrationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class RegistrationService {

    @Autowired
    private RegistrationRepository registrationRepository;

    public Registration save(Registration registration) {
        return registrationRepository.save(registration);
    }

    public boolean isUserAlreadyRegisteredToToEvent(User user, Event event) {
        return registrationRepository.existsByUserAndEvent(user, event);
    }

    public List<Registration> findAllByEventId (UUID eventId) {
        return registrationRepository.findAllByEvent_Id(eventId);
    }
}
