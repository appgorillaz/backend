package com.gorillaz.app.controller;

import com.gorillaz.app.domain.event.Event;
import com.gorillaz.app.domain.post.GetPostDTO;
import com.gorillaz.app.domain.post.NewPostDTO;
import com.gorillaz.app.domain.post.Post;
import com.gorillaz.app.domain.user.User;
import com.gorillaz.app.service.EventService;
import com.gorillaz.app.service.PostService;
import com.gorillaz.app.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/posts")
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

            if (event.isEmpty()) {
               return ResponseEntity.badRequest().body("Evento inválido!");
            }
            eventId = event.get();
        }

        var currentDate = LocalDate.now();

        Post post = new Post(data.title(), data.subtitle(), data.text(), currentDate, user, eventId);
        var postCreated = this.postService.save(post);

        if (postCreated == null) {
            return ResponseEntity.badRequest().body("Erro ao criar o post");
        }

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping
    public ResponseEntity<Map<String, Object>>getAllPosts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "6") int size
    ) {
        try {
            Pageable paging = PageRequest.of(page, size);

            Page<Post> pagePosts = postService.getAll(paging);

            List<GetPostDTO> posts = pagePosts.getContent().stream()
                        .map(post -> postService.FormatPostToDTO(post)
                        )
                        .collect(Collectors.toList());

            Map<String, Object> response = new HashMap<>();

            response.put("posts", posts);
            response.put("currentPage", pagePosts.getNumber() + 1);
            response.put("totalItems", pagePosts.getTotalElements());
            response.put("totalPages", pagePosts.getTotalPages());
            response.put("hasNext", pagePosts.hasNext());

            return new ResponseEntity<>(response, HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<GetPostDTO> getPost(@PathVariable UUID id) {
        var post = postService.getPost(id);

        if (post == null) {
            return ResponseEntity.notFound().build();
        }

        GetPostDTO postDto = postService.FormatPostToDTO(post);

        return ResponseEntity.ok(postDto);
    }
}
