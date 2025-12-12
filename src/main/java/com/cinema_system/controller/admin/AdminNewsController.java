package com.cinema_system.controller.admin;

import com.cinema_system.model.News;
import com.cinema_system.service.NewsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@Controller
@RequestMapping("/admin/news")
@RequiredArgsConstructor
public class AdminNewsController {

    private final NewsService newsService;

    @GetMapping
    public String newsList(Model model) {
        model.addAttribute("news", newsService.findAll());
        return "admin/news/list";
    }

    @GetMapping("/create")
    public String createForm(Model model) {
        model.addAttribute("newsForm", new News());
        return "admin/news/form";
    }

    @PostMapping("/create")
    public String createNews(@ModelAttribute("newsForm") News newsForm) {
        newsForm.setCreatedAt(LocalDateTime.now());
        newsService.save(newsForm);
        return "redirect:/admin/news";
    }

    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable Long id, Model model) {
        News news = newsService.findById(id).orElseThrow(() -> new RuntimeException("News not found"));
        model.addAttribute("newsForm", news);
        return "admin/news/form";
    }

    @PostMapping("/edit/{id}")
    public String updateNews(@PathVariable Long id, @ModelAttribute("newsForm") News newsForm) {
        newsForm.setId(id);
        newsService.save(newsForm);
        return "redirect:/admin/news";
    }

    @PostMapping("/delete/{id}")
    public String deleteNews(@PathVariable Long id) {
        newsService.deleteById(id);
        return "redirect:/admin/news";
    }
}