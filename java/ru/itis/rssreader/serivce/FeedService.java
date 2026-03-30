package ru.itis.rssreader.serivce;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.itis.rssreader.entity.Post;
import ru.itis.rssreader.entity.RssSource;
import ru.itis.rssreader.repository.PostRepository;

import java.util.List;

@Service
public class FeedService {

    private static final int PAGE_SIZE = 10;

    private final RssSourceService rssSourceService;
    private final RssParserService rssParserService;
    private final PostRepository postRepository;

    @Autowired
    public FeedService(RssSourceService rssSourceService,
                       RssParserService rssParserService,
                       PostRepository postRepository) {
        this.rssSourceService = rssSourceService;
        this.rssParserService = rssParserService;
        this.postRepository = postRepository;
    }

    public List<Post> getUserFeed(Long userId, int page) {

        List<RssSource> sources = rssSourceService.getUserSources(userId);

        for (RssSource source : sources) {
            rssParserService.fetchAndParseFeed(source.getUrl(), source.getId());
        }

        int offset = page * PAGE_SIZE;
        return postRepository.findByUserIdPaginated(userId, offset, PAGE_SIZE);
    }

    public long getTotalPostsCount(Long userId) {
        return postRepository.countByUserId(userId);
    }

    public int getPageSize() {
        return PAGE_SIZE;
    }
}