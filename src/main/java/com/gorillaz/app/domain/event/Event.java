package com.gorillaz.app.domain.event;

import com.gorillaz.app.domain.post.Post;
import com.gorillaz.app.domain.user.User;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "events")
@Data
@NoArgsConstructor
public class Event {
    @Id
    @GeneratedValue(generator = "UUID")
    private UUID id;

    @Column(nullable = false)
    private String title;
    @Column(nullable = false)
    private Timestamp startDate;
    @Column(nullable = false)
    private Timestamp endDate;
    @Column(nullable = false)
    private String location;
    @Column(nullable = false)
    private String type;

    @ManyToOne
    @JoinColumn(name = "adm_id", referencedColumnName = "id")
    private User admId;

    @OneToMany(mappedBy = "eventId")
    Set<Post> posts;

    public Event(String id) {
        this.id = UUID.fromString(id);
    }

   public Event(String title, Timestamp startDate, Timestamp endDate, String location, String type, User admId) {
       this.title = title;
       this.startDate = startDate;
       this.endDate = endDate;
       this.location = location;
       this.type = type;
       this.admId = admId;
   }
}
