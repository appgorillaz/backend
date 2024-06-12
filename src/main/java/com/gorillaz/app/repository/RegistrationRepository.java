package com.gorillaz.app.repository;

import com.gorillaz.app.domain.event.Event;
import com.gorillaz.app.domain.event.Registration;
import com.gorillaz.app.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface RegistrationRepository extends JpaRepository<Registration, UUID> {
    boolean existsByUserAndEvent(User user, Event event);

    List<Registration> findAllByEvent_Id(UUID eventId);
}
