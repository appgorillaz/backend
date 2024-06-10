package com.gorillaz.app.domain.event;

import com.gorillaz.app.domain.user.User;

import java.time.LocalDateTime;

public record NewEventDTO(String eventName, LocalDateTime startDate, LocalDateTime endDate, String location, String type, User user) {
}
