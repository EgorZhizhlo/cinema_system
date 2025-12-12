package com.cinema_system.controller.admin;

import com.cinema_system.model.Hall;
import com.cinema_system.service.HallService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin/halls")
@RequiredArgsConstructor
public class AdminHallController {
    private final HallService hallService;

    @GetMapping
    public String listHalls(Model model) {
        model.addAttribute("halls", hallService.getAllHalls());
        return "admin/halls/list";
    }

    @GetMapping("/create")
    public String createHallForm(Model model) {
        model.addAttribute("hall", new Hall());
        return "admin/halls/form";
    }

    @PostMapping("/create")
    public String createHall(@ModelAttribute Hall hall, RedirectAttributes redirectAttributes) {
        hallService.saveHall(hall);
        redirectAttributes.addFlashAttribute("message", "Зал создан");
        return "redirect:/admin/halls";
    }

    @GetMapping("/{id}/edit")
    public String editHallForm(@PathVariable Long id, Model model) {
        Hall hall = hallService.getHallById(id);
        if (hall == null) {
            return "redirect:/admin/halls";
        }
        model.addAttribute("hall", hall);
        return "admin/halls/form";
    }

    @PostMapping("/{id}/edit")
    public String editHall(@PathVariable Long id, @ModelAttribute Hall hall, RedirectAttributes redirectAttributes) {
        hall.setId(id);
        hallService.saveHall(hall);
        redirectAttributes.addFlashAttribute("message", "Зал обновлен");
        return "redirect:/admin/halls";
    }

    @PostMapping("/{id}/delete")
    public String deleteHall(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        hallService.deleteHall(id);
        redirectAttributes.addFlashAttribute("message", "Зал удален");
        return "redirect:/admin/halls";
    }
}