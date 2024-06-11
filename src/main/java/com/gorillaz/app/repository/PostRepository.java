package com.gorillaz.app.repository;

import com.gorillaz.app.domain.post.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PostRepository extends JpaRepository<Post, UUID> {

    Post getPostById(UUID id);
}
