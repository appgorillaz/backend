package com.gorillaz.app.domain.post;


import java.time.LocalDate;
import java.util.UUID;

public record GetPostDTO(UUID postId, String title, String subtitle, String text, LocalDate postDate, String author, UUID eventID) {
}
