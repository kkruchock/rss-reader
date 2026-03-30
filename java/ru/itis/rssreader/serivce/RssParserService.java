package ru.itis.rssreader.serivce;

import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.SyndFeedInput;
import com.rometools.rome.io.XmlReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.itis.rssreader.entity.Post;
import ru.itis.rssreader.repository.PostRepository;

import java.net.URL;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class RssParserService {

    private final PostRepository postRepository;

    @Autowired
    public RssParserService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    /**
     * Парсит RSS фид и сохраняет новые посты
     * @param feedUrl URL RSS ленты
     * @param sourceId ID источника в БД
     * @return список новых сохраненных постов
     */
    public List<Post> fetchAndParseFeed(String feedUrl, Long sourceId) {
        List<Post> newPosts = new ArrayList<>();

        try {
            //парсим
            URL url = new URL(feedUrl);
            SyndFeedInput input = new SyndFeedInput();
            SyndFeed feed = input.build(new XmlReader(url));

            //проходимся по каждой записи
            for (SyndEntry entry : feed.getEntries()) {
                String link = entry.getLink();

                //скипаем существующие
                if (postRepository.existsBySourceIdAndLink(sourceId, link)) {
                    continue;
                }

                //создаем новый пост
                Post post = mapToPost(entry, sourceId);
                postRepository.save(post);
                newPosts.add(post);
            }
        } catch (Exception e) {
            throw new RuntimeException("Ошибка при парсинге RSS: " + e.getMessage(), e);
        }

        return newPosts;
    }

    private Post mapToPost(SyndEntry entry, Long sourceId) {
        //получаем дату
        LocalDateTime publishedAt = null;
        Date pubDate = entry.getPublishedDate();
        if (pubDate != null) {
            publishedAt = pubDate.toInstant()
                    .atZone(ZoneId.systemDefault())
                    .toLocalDateTime();
        }

        //получаем описанние
        String description = null;
        if (entry.getDescription() != null) {
            description = entry.getDescription().getValue();
        }

        return Post.builder()
                .sourceId(sourceId)
                .title(entry.getTitle())
                .description(description)
                .link(entry.getLink())
                .guid(entry.getUri())
                .publishedAt(publishedAt)
                .build();
    }
}