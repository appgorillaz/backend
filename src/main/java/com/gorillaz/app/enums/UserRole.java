package com.gorillaz.app.enums;

import lombok.Getter;

@Getter
public enum UserRole {
    ADMIN("admin"), USER("user"), GUEST("guest");

    private final String role;

   UserRole(String role) {
        this.role = role;
    }

}
