package com.gorillaz.app.domain.event;

import java.time.LocalDateTime;
import java.util.UUID;

public record EventDTO(UUID id, String title, LocalDateTime startDate, LocalDateTime endDate, String location, String type) {
}
