package com.cinema_system.controller.site;

import com.cinema_system.service.NewsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/news")
@RequiredArgsConstructor
public class NewsController {

    private final NewsService newsService;

    @GetMapping
    public String newsList(Model model) {
        model.addAttribute("news", newsService.findAll());
        return "site/news/list";
    }

    @GetMapping("/{id}")
    public String newsDetail(@PathVariable Long id, Model model) {
        model.addAttribute("news", newsService.findById(id).orElseThrow(() -> new RuntimeException("News not found")));
        return "site/news/detail";
    }
}