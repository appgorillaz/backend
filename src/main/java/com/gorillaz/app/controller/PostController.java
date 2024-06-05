package com.gorillaz.app.controller;

import com.gorillaz.app.domain.post.NewPostDTO;
import com.gorillaz.app.domain.post.Post;
import com.gorillaz.app.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/post")
public class PostController {
    @Autowired
    private PostService postService;

    @PostMapping
    public ResponseEntity newPost(@RequestBody @Validated NewPostDTO data) {
        // TODO pegar o user atual e atribuir ao post
        Post post = new Post(data.title(), data.subtitle(), data.text(), data.postDate());

        var postCreated = this.postService.save(post);

        if (postCreated == null) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping
    public ResponseEntity getAllPosts() {
        return ResponseEntity.ok("Oi");
    }
}
