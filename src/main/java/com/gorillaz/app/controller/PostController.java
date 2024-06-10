package com.gorillaz.app.controller;

import com.gorillaz.app.domain.event.Event;
import com.gorillaz.app.domain.post.NewPostDTO;
import com.gorillaz.app.domain.post.Post;
import com.gorillaz.app.domain.user.User;
import com.gorillaz.app.service.EventService;
import com.gorillaz.app.service.PostService;
import com.gorillaz.app.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/post")
public class PostController {
    @Autowired
    private PostService postService;

    @Autowired
    private UserService userService;

    @Autowired
    private EventService eventService;

    @PostMapping
    public ResponseEntity newPost(@RequestBody @Validated NewPostDTO data) {

        User user = userService.getAuthenticatedUser();

        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Usuário não autenticado");
        }

        Event eventId = data.eventId();

        if (eventId != null) {
            Optional<Event> event = eventService.findEventById(data.eventId().getId());

            if (event.isPresent()) {
                eventId = event.get();
            }
        }

        Post post = new Post(data.title(), data.subtitle(), data.text(), data.postDate(), user, eventId);
        var postCreated = this.postService.save(post);

        if (postCreated == null) {
            return ResponseEntity.badRequest().body("Erro ao criar o post");
        }

        return ResponseEntity.status(HttpStatus.CREATED).build();
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
