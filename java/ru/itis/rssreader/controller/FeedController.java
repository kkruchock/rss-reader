package ru.itis.rssreader.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.itis.rssreader.entity.Post;
import ru.itis.rssreader.serivce.FeedService;
import ru.itis.rssreader.serivce.RssSourceService;

import java.util.List;

@Controller
public class FeedController {

    private final FeedService feedService;
    private final RssSourceService rssSourceService;

    @Autowired
    public FeedController(FeedService feedService, RssSourceService rssSourceService) {
        this.feedService = feedService;
        this.rssSourceService = rssSourceService;
    }

    @GetMapping("/feed")
    public String showFeed(@RequestParam(name = "page", defaultValue = "0") int page,
                           HttpSession session,
                           Model model) {

        Long userId = (Long) session.getAttribute("userId");

        List<Post> posts = feedService.getUserFeed(userId, page);

        long totalPosts = feedService.getTotalPostsCount(userId);
        int pageSize = feedService.getPageSize();
        int totalPages = (int) Math.ceil((double) totalPosts / pageSize);

        model.addAttribute("posts", posts);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("hasSources", !rssSourceService.getUserSources(userId).isEmpty());

        return "feed";
    }
}