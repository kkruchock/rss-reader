package ru.itis.rssreader.repository;

import ru.itis.rssreader.entity.Post;
import java.util.List;
import java.util.Optional;

public interface PostRepository {

    void save(Post post);

    boolean existsBySourceIdAndLink(Long sourceId, String link);

    List<Post> findByUserIdPaginated(Long userId, int offset, int limit);

    long countByUserId(Long userId);

    Optional<Post> findById(Long id);
}