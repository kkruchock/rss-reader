package ru.itis.rssreader.repository;

import ru.itis.rssreader.entity.RssSource;

import java.util.List;
import java.util.Optional;

public interface RssSourceRepository {

    RssSource save(RssSource source);

    Optional<RssSource> findById(Long sourceId);

    List<RssSource> findByUserId(Long userId);

    Boolean existsByUserIdAndUrl(Long userId, String url);
}
