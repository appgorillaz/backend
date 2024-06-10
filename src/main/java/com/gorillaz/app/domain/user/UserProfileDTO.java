package com.gorillaz.app.domain.user;

import com.gorillaz.app.enums.Gender;
import com.gorillaz.app.enums.Period;

public record UserProfileDTO(String name, String email, Gender gender, String ra, String course, Period period, Integer semester, boolean representative) {
}
