package com.cinema_system.controller.admin;

import com.cinema_system.model.Session;
import com.cinema_system.service.HallService;
import com.cinema_system.service.MovieService;
import com.cinema_system.service.SessionService;
import com.cinema_system.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin/sessions")
@RequiredArgsConstructor
public class AdminSessionController {
    private final SessionService sessionService;
    private final MovieService movieService;
    private final HallService hallService;
    private final UserService userService;

    @GetMapping
    public String listSessions(Model model) {
        model.addAttribute("sessions", sessionService.getAllSessions());
        return "admin/sessions/list";
    }

    @GetMapping("/create")
    public String createSessionForm(Model model) {
        model.addAttribute("sess", new Session());
        model.addAttribute("movies", movieService.getAllMovies());
        model.addAttribute("halls", hallService.getAllHalls());
        model.addAttribute("managers", userService.getAllUsers().stream().filter(u -> u.getRole().name().equals("MANAGER")).toList());
        return "admin/sessions/form";
    }

    @PostMapping("/create")
    public String createSession(@ModelAttribute Session session, RedirectAttributes redirectAttributes) {
        sessionService.saveSession(session);
        redirectAttributes.addFlashAttribute("message", "Сеанс создан");
        return "redirect:/admin/sessions";
    }

    @GetMapping("/{id}/edit")
    public String editSessionForm(@PathVariable Long id, Model model) {
        Session session = sessionService.getSessionById(id);
        if (session == null) {
            return "redirect:/admin/sessions";
        }
        model.addAttribute("sess", session);
        model.addAttribute("movies", movieService.getAllMovies());
        model.addAttribute("halls", hallService.getAllHalls());
        model.addAttribute("managers", userService.getAllUsers().stream().filter(u -> u.getRole().name().equals("MANAGER")).toList());
        return "admin/sessions/form";
    }

    @PostMapping("/{id}/edit")
    public String editSession(@PathVariable Long id, @ModelAttribute Session session, RedirectAttributes redirectAttributes) {
        session.setId(id);
        sessionService.saveSession(session);
        redirectAttributes.addFlashAttribute("message", "Сеанс обновлен");
        return "redirect:/admin/sessions";
    }

    @PostMapping("/{id}/delete")
    public String deleteSession(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        sessionService.deleteSession(id);
        redirectAttributes.addFlashAttribute("message", "Сеанс удален");
        return "redirect:/admin/sessions";
    }
}