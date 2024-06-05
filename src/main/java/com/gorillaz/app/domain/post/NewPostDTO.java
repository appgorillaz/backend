package com.gorillaz.app.domain.post;

import java.time.LocalDate;

public record NewPostDTO(String title, String subtitle, String text, LocalDate postDate) {
}
