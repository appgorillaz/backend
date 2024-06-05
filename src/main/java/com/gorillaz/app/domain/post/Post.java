package com.gorillaz.app.domain.post;

import com.gorillaz.app.domain.event.Event;
import com.gorillaz.app.domain.user.User;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "posts")
@Data
@NoArgsConstructor
public class Post {
    @Id
    @GeneratedValue(generator = "UUID")
    private UUID id;
    @CreationTimestamp
    private LocalDate postDate;
    @Column(nullable = false)
    private String title;
    private String subtitle;
    @Column(nullable = false, columnDefinition = "TEXT")
    private String text;

    @ManyToOne
    @JoinColumn(name = "adm_id")
    private User admId;
    @ManyToOne
    @JoinColumn(name = "event_id")
    private Event eventId;

    public Post(String title, String subtitle, String text, LocalDate postDate) {
        this.title = title;
        this.subtitle = subtitle;
        this.text = text;
        this.postDate = postDate;
    }
}
