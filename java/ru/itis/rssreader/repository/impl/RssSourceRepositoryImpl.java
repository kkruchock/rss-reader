package ru.itis.rssreader.repository.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.itis.rssreader.entity.RssSource;
import ru.itis.rssreader.repository.RssSourceRepository;
import ru.itis.rssreader.repository.mapper.RssSourceRowMapper;

import java.util.List;
import java.util.Optional;

@Component
public class RssSourceRepositoryImpl implements RssSourceRepository {

    private final JdbcTemplate jdbcTemplate;
    private final RssSourceRowMapper rssSourceRowMapper;

    @Autowired
    public RssSourceRepositoryImpl(JdbcTemplate jdbcTemplate, RssSourceRowMapper rssSourceRowMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.rssSourceRowMapper = rssSourceRowMapper;
    }


    @Override
    public RssSource save(RssSource source) {
        String sql = "INSERT INTO rss_sources (user_id, name, url) VALUES (?, ?, ?)";
        jdbcTemplate.update(sql, source.getUserId(), source. getName(), source.getUrl());

        return findByUserIdAndUrl(source.getUserId(), source.getUrl());
    }

    @Override
    public Optional<RssSource> findById(Long sourceId) {
        String sql = "SELECT * FROM rss_sources WHERE id = ?";
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(sql, rssSourceRowMapper, sourceId));
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public List<RssSource> findByUserId(Long userId) {
        String sql = "SELECT * FROM rss_sources WHERE user_id = ? ORDER BY id";
        return jdbcTemplate.query(sql, rssSourceRowMapper, userId);
    }

    @Override
    public Boolean existsByUserIdAndUrl(Long userId, String url) {
        String sql = "SELECT COUNT(*) FROM rss_sources WHERE user_id = ? AND url = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, userId, url);
        return count != null && count > 0;
    }


    private RssSource findByUserIdAndUrl(Long userId, String url) {
        String sql = "SELECT * FROM rss_sources WHERE user_id = ? AND url = ?";
        try {
            return jdbcTemplate.queryForObject(sql, rssSourceRowMapper, userId, url);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }
}