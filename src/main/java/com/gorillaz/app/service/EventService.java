package com.gorillaz.app.service;

import com.gorillaz.app.domain.event.Event;
import com.gorillaz.app.repository.EventRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class EventService {
    private final EventRepository eventRepository;

    public EventService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    public Event save(Event event) {
       return eventRepository.save(event);
    }

    public Optional<Event> findEventById(UUID eventId) {
        return eventRepository.findById(eventId);
    }

    public List<Event> getEvents() {
        return eventRepository.findAll();
    }
}
