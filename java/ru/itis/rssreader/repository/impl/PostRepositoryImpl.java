package ru.itis.rssreader.repository.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.itis.rssreader.entity.Post;
import ru.itis.rssreader.repository.PostRepository;
import ru.itis.rssreader.repository.mapper.PostRowMapper;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Repository
public class PostRepositoryImpl implements PostRepository {

    private final JdbcTemplate jdbcTemplate;
    private final PostRowMapper postRowMapper;

    @Autowired
    public PostRepositoryImpl(JdbcTemplate jdbcTemplate, PostRowMapper postRowMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.postRowMapper = postRowMapper;
    }

    @Override
    public void save(Post post) {
        String sql = "INSERT INTO posts (source_id, title, description, link, guid, published_at) " +
                "VALUES (?, ?, ?, ?, ?, ?)";

        jdbcTemplate.update(sql,
                post.getSourceId(),
                post.getTitle(),
                post.getDescription(),
                post.getLink(),
                post.getGuid(),
                post.getPublishedAt() != null ? Timestamp.valueOf(post.getPublishedAt()) : null
        );
    }

    @Override
    public boolean existsBySourceIdAndLink(Long sourceId, String link) {
        String sql = "SELECT COUNT(*) FROM posts WHERE source_id = ? AND link = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, sourceId, link);
        return count != null && count > 0;
    }

    @Override
    public List<Post> findByUserIdPaginated(Long userId, int offset, int limit) {
        String sql = "SELECT p.*, rs.name as source_name FROM posts p " +
                "JOIN rss_sources rs ON p.source_id = rs.id " +
                "WHERE rs.user_id = ? " +
                "ORDER BY p.published_at DESC, p.created_at DESC " +
                "LIMIT ? OFFSET ?";

        return jdbcTemplate.query(sql, postRowMapper, userId, limit, offset);
    }

    @Override
    public long countByUserId(Long userId) {
        String sql = "SELECT COUNT(*) FROM posts p " +
                "JOIN rss_sources rs ON p.source_id = rs.id " +
                "WHERE rs.user_id = ?";

        Long count = jdbcTemplate.queryForObject(sql, Long.class, userId);
        return count != null ? count : 0;
    }

    @Override
    public Optional<Post> findById(Long id) {
        String sql = "SELECT * FROM posts WHERE id = ?";
        try {
            Post post = jdbcTemplate.queryForObject(sql, postRowMapper, id);
            return Optional.ofNullable(post);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<Post> findByIdAndUserId(Long postId, Long userId) {
        String sql = "SELECT p.*, rs.name as source_name FROM posts p " +
                "JOIN rss_sources rs ON p.source_id = rs.id " +
                "WHERE p.id = ? AND rs.user_id = ?";
        try {
            Post post = jdbcTemplate.queryForObject(sql, postRowMapper, postId, userId);
            return Optional.ofNullable(post);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }
}