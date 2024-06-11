package com.gorillaz.app.domain.post;

import com.gorillaz.app.domain.event.Event;

import java.time.LocalDate;
import java.util.UUID;

public record GetPostDTO(String title, String subtitle, String text, LocalDate postDate, String author, UUID eventID) {
}
