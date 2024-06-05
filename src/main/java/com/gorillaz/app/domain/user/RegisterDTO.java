package com.gorillaz.app.domain.user;

import com.gorillaz.app.enums.Gender;
import com.gorillaz.app.enums.UserRole;

public record RegisterDTO(String name, String email, String password, Gender gender, String course, UserRole role) {
}
