package com.gorillaz.app.domain.event;

import java.sql.Timestamp;

public record NewEventDTO(String eventName, Timestamp startDate, Timestamp endDate, String location, String type) {
}
