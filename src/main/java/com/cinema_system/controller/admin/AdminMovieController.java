package com.cinema_system.controller.admin;

import com.cinema_system.model.Movie;
import com.cinema_system.service.MovieService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin/movies")
@RequiredArgsConstructor
public class AdminMovieController {
    private final MovieService movieService;

    @GetMapping
    public String listMovies(Model model) {
        model.addAttribute("movies", movieService.getAllMovies());
        return "admin/movies/list";
    }

    @GetMapping("/create")
    public String createMovieForm(Model model) {
        model.addAttribute("movie", new Movie());
        return "admin/movies/form";
    }

    @PostMapping("/create")
    public String createMovie(@ModelAttribute Movie movie, RedirectAttributes redirectAttributes) {
        movieService.saveMovie(movie);
        redirectAttributes.addFlashAttribute("message", "Фильм создан");
        return "redirect:/admin/movies";
    }

    @GetMapping("/{id}/edit")
    public String editMovieForm(@PathVariable Long id, Model model) {
        Movie movie = movieService.getMovieById(id);
        if (movie == null) {
            return "redirect:/admin/movies";
        }
        model.addAttribute("movie", movie);
        return "admin/movies/form";
    }

    @PostMapping("/{id}/edit")
    public String editMovie(@PathVariable Long id, @ModelAttribute Movie movie, RedirectAttributes redirectAttributes) {
        movie.setId(id);
        movieService.saveMovie(movie);
        redirectAttributes.addFlashAttribute("message", "Фильм обновлен");
        return "redirect:/admin/movies";
    }

    @PostMapping("/{id}/delete")
    public String deleteMovie(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        movieService.deleteMovie(id);
        redirectAttributes.addFlashAttribute("message", "Фильм удален");
        return "redirect:/admin/movies";
    }
}