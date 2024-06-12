package com.gorillaz.app.domain.event;

import com.gorillaz.app.domain.user.User;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Table(name = "registrations")
@Data
@NoArgsConstructor
public class Registration {
    @Id
    @GeneratedValue(generator = "UUID")
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "event_id", referencedColumnName = "id")
    private Event event;

    public Registration(User userId, Event eventId) {
        this.user = userId;
        this.event = eventId;
    }
}
