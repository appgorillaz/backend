package com.gorillaz.app.model;

import com.gorillaz.app.enums.Gender;
import com.gorillaz.app.enums.Period;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private Gender gender;

    @Column(unique = true)
    private String ra;

    @Column(nullable = false)
    private String course;

    private Period period;

    @Column(nullable = false, columnDefinition = "BOOLEAN default 'FALSE'")
    private boolean is_representative;

    private int semester;

    @Column(nullable = false, columnDefinition = "BOOLEAN default 'FALSE'")
    private boolean is_adm;

    private String role;
}
