package com.gorillaz.app.service;

import com.gorillaz.app.domain.post.Post;
import com.gorillaz.app.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PostService {
    private final PostRepository postRepository;

    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }
    public Post save(Post post) {
        return postRepository.save(post);
    }
}
