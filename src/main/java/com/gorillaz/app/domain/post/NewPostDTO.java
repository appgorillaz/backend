package com.gorillaz.app.domain.post;

import com.gorillaz.app.domain.event.Event;

import java.time.LocalDate;

public record NewPostDTO(String title, String subtitle, String text, LocalDate postDate, Event eventId) {
}
