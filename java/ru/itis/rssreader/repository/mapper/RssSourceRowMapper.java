package ru.itis.rssreader.repository.mapper;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import ru.itis.rssreader.entity.RssSource;


import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class RssSourceRowMapper implements RowMapper<RssSource> {

    @Override
    public RssSource mapRow(ResultSet rs, int rowNum) throws SQLException {
        return RssSource.builder()
                .id(rs.getLong("id"))
                .userId(rs.getLong( "user_id"))
                .name(rs.getString("name"))
                .url(rs.getString("url"))
                .createdAt(rs.getTimestamp("created_at") != null
                        ? rs.getTimestamp("created_at").toLocalDateTime()
                        : null)
                .build();
    }
}