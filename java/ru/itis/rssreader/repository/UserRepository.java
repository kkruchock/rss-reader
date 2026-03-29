package ru.itis.rssreader.repository;

import ru.itis.rssreader.entity.User;
import java.util.Optional;

public interface UserRepository {

    User save(User user);

    Optional<User> findByUsername(String username);

    Optional<User> findById(Long id);
}
