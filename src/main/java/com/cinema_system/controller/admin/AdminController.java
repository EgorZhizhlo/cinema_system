package com.cinema_system.controller.admin;

import com.cinema_system.service.UserService;
import com.cinema_system.service.HallService;
import com.cinema_system.service.MovieService;
import com.cinema_system.service.NewsService;
import com.cinema_system.service.BookingService;
import com.cinema_system.service.SessionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final UserService userService;
    private final HallService hallService;
    private final MovieService movieService;
    private final NewsService newsService;
    private final BookingService bookingService;
    private final SessionService sessionService;

    @GetMapping
    public String dashboard(Model model) {
        model.addAttribute("usersCount", userService.countAll());
        model.addAttribute("hallsCount", hallService.countAll());
        model.addAttribute("moviesCount", movieService.countAll());
        model.addAttribute("newsCount", newsService.countAll());
        model.addAttribute("bookingsCount", bookingService.countAll());
        model.addAttribute("sessionsCount", sessionService.countAll());
        return "admin/dashboard";
    }
}
