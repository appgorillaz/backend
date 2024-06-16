package com.gorillaz.app.service;

import com.gorillaz.app.domain.post.GetPostDTO;
import com.gorillaz.app.domain.post.Post;
import com.gorillaz.app.repository.PostRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class PostService {
    private final PostRepository postRepository;

    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }
    public Post save(Post post) {
        return postRepository.save(post);
    }

    public Page<Post> getAll(Pageable pageable) {
        return postRepository.findAll(pageable);
    }

    public Post getPost(UUID id) {
        return postRepository.getPostById(id);
    }

    public GetPostDTO FormatPostToDTO(Post post) {
        if (post == null)
            return null;

        var eventId = post.getEventId() != null ? post.getEventId().getId() : null;

        return new GetPostDTO(post.getId(), post.getTitle(), post.getSubtitle(), post.getText(), post.getPostDate(), post.getAdmId().getName(), eventId);
    }
}
