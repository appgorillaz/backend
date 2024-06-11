package com.gorillaz.app.domain.user;

import com.gorillaz.app.enums.Gender;
import com.gorillaz.app.enums.Period;
import com.gorillaz.app.enums.UserRole;

public record RegisterDTO(String name, String email, String password, String ra, Gender gender, String course, int semester, Period period, boolean isRepresentative, UserRole role) {
}
