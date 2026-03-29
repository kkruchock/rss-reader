package ru.itis.rssreader.repository.mapper;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import ru.itis.rssreader.entity.Post;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class PostRowMapper implements RowMapper<Post> {

    @Override
    public Post mapRow(ResultSet rs, int rowNum) throws SQLException {
        return Post.builder()
                .id(rs.getLong("id"))
                .sourceId(rs.getLong("source_id"))
                .title(rs.getString("title"))
                .description(rs.getString("description"))
                .link(rs.getString("link"))
                .guid(rs.getString("guid"))
                .publishedAt(rs.getTimestamp("published_at") != null
                        ? rs.getTimestamp("published_at").toLocalDateTime()
                        : null)
                .createdAt(rs.getTimestamp("created_at") != null
                        ? rs.getTimestamp("created_at").toLocalDateTime()
                        : null)
                .build();
    }
}