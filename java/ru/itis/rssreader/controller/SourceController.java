package ru.itis.rssreader.controller;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import ru.itis.rssreader.dto.RssSourceDto;
import ru.itis.rssreader.entity.RssSource;
import ru.itis.rssreader.exception.AuthException;
import ru.itis.rssreader.serivce.RssSourceService;

import java.util.List;

@Controller
public class SourceController {

    private final RssSourceService rssSourceService;

    @Autowired
    public SourceController(RssSourceService rssSourceService) {
        this.rssSourceService = rssSourceService;
    }

    @GetMapping("/sources")
    public String showSources(Model model, HttpSession session) {

        List<RssSource> sources = rssSourceService.getUserSources( (Long) session.getAttribute("userId"));

        model.addAttribute("sources", sources);

        model.addAttribute("rssSourceDto", new RssSourceDto());

        return "source";
    }

    @PostMapping("/sources")
    public String addSource(
                            @Valid @ModelAttribute("rssSourceDto") RssSourceDto dto,
                            BindingResult result,
                            HttpSession session,
                            Model model) {

        Long userId = (Long) session.getAttribute("userId");

        if (result.hasErrors()) {
            List<RssSource> sources = rssSourceService.getUserSources(userId);
            model.addAttribute("sources", sources);
            return "sources";
        }

        try {
            rssSourceService.addSource(userId, dto.getName(), dto.getUrl());
            return "redirect:/sources";
        } catch (AuthException e) {
            model.addAttribute("error", e.getMessage());
            List<RssSource> sources = rssSourceService.getUserSources(userId);
            model.addAttribute("sources", sources);
            return "sources";
        }
    }
}