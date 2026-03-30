package ru.itis.rssreader.serivce;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.itis.rssreader.entity.RssSource;
import ru.itis.rssreader.exception.AuthException;
import ru.itis.rssreader.repository.RssSourceRepository;

import java.util.List;

@Service
public class RssSourceService {

    private final RssSourceRepository rssSourceRepository;

    @Autowired
    public RssSourceService(RssSourceRepository rssSourceRepository) {
        this.rssSourceRepository = rssSourceRepository;
    }

    public void addSource(Long userId, String name, String url) {

        if (rssSourceRepository.existsByUserIdAndUrl(userId, url)) {
            throw new AuthException("Вы уже добавили этот RSS источник");
        }

        RssSource source = RssSource.builder()
                .userId(userId)
                .name(name)
                .url(url)
                .build();

        rssSourceRepository.save(source);
    }

    public List<RssSource> getUserSources(Long userId) {
        return rssSourceRepository.findByUserId(userId);
    }
}