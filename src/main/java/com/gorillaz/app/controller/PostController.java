package com.gorillaz.app.controller;

import com.gorillaz.app.domain.post.NewPostDTO;
import com.gorillaz.app.domain.post.Post;
import com.gorillaz.app.domain.user.User;
import com.gorillaz.app.service.PostService;
import com.gorillaz.app.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/post")
public class PostController {
    @Autowired
    private PostService postService;

    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity newPost(@RequestBody @Validated NewPostDTO data) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth != null) {
            String username = auth.getName();
            User user = userService.getUserByEmail(username);

            if (user != null) {
                Post post = new Post(data.title(), data.subtitle(), data.text(), data.postDate(), user);

                var postCreated = this.postService.save(post);

                if (postCreated == null) {
                    return ResponseEntity.badRequest().body("Erro ao criar o post");
                }

                return ResponseEntity.status(HttpStatus.CREATED).build();
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Usuário não autenticado");
            }
        } else
            return ResponseEntity.badRequest().build();
    }

    @GetMapping
    public ResponseEntity getAllPosts() {
        var posts = postService.getAll();

        if (posts.isEmpty()) {
            return ResponseEntity.ok().body("");
        }

        return ResponseEntity.ok().body(posts);
    }
}
