package com.gorillaz.app.domain.post;

import com.gorillaz.app.domain.user.User;

import java.time.LocalDate;

public record NewPostDTO(String title, String subtitle, String text, LocalDate postDate, User userId) {
}
