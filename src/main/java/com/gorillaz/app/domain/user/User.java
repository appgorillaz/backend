package com.gorillaz.app.domain.user;

import com.gorillaz.app.domain.event.Event;
import com.gorillaz.app.domain.post.Post;
import com.gorillaz.app.enums.Gender;
import com.gorillaz.app.enums.Period;
import com.gorillaz.app.enums.UserRole;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Entity(name = "users")
@Table(name = "users")
@Data
public class User implements UserDetails {
    @Id
    @GeneratedValue(generator = "UUID")
    private UUID id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private Gender gender;

    @Column(unique = true)
    private String ra;

    @Column(nullable = false)
    private String course;

    private Period period;

    @Column(name = "is_representative", nullable = false, columnDefinition = "BOOLEAN default 'FALSE'")
    private boolean isRepresentative;

    private int semester;

    private UserRole role;

    @OneToMany(mappedBy = "admId")
    Set<Post> posts;

    @OneToMany(mappedBy = "admId")
    Set<Event> events;

    public User(String name, String email, String password, String ra, Gender gender, String course, Period period, boolean isRepresentative, UserRole role) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.ra = ra;
        this.gender = gender;
        this.course = course;
        this.period = period;
        this.isRepresentative = isRepresentative;
        this.role = role;
    }

    @CreationTimestamp
    private LocalDateTime createdAt;

    public User() {

    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (this.role == UserRole.ADMIN)
            return List.of(new SimpleGrantedAuthority("ROLE_ADMIN"), new SimpleGrantedAuthority("ROLE_USER"));
        else
            return List.of(new SimpleGrantedAuthority("ROLE_USER"));
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
