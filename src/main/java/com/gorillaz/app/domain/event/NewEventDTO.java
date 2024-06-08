package com.gorillaz.app.domain.event;

import com.gorillaz.app.domain.user.User;

import java.sql.Timestamp;

public record NewEventDTO(String eventName, Timestamp startDate, Timestamp endDate, String location, String type, User user) {
}
