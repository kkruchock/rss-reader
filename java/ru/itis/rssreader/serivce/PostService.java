package ru.itis.rssreader.serivce;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.itis.rssreader.entity.Post;
import ru.itis.rssreader.exception.AuthException;
import ru.itis.rssreader.repository.PostRepository;

@Service
public class PostService {

    private final PostRepository postRepository;

    @Autowired
    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public Post getPostById(Long postId, Long userId) {
        return postRepository.findByIdAndUserId(postId, userId)
                .orElseThrow(() -> new AuthException("Пост не найден или у вас нет доступа"));
    }
}