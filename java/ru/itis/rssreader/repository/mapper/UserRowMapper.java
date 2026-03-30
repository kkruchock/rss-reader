package ru.itis.rssreader.repository.mapper;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import ru.itis.rssreader.entity.User;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class UserRowMapper  implements RowMapper<User> {

    @Override
    public User mapRow(ResultSet rs, int rowNum) throws SQLException {
        return User.builder()
                .id(rs.getLong("id"))
                .username(rs.getString("username"))
                .passwordHash(rs.getString("password_hash"))
                .createdAt(rs.getTimestamp("created_at") != null
                        ? rs.getTimestamp("created_at").toLocalDateTime()
                        : null)
                .build();
    }
}
