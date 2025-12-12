package com.cinema_system.controller.site;

import com.cinema_system.model.Booking;
import com.cinema_system.model.BookingStatus;
import com.cinema_system.model.Session;
import com.cinema_system.model.User;
import com.cinema_system.service.BookingService;
import com.cinema_system.service.SessionService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequestMapping("/bookings")
@RequiredArgsConstructor
public class BookingController {
    private final BookingService bookingService;
    private final SessionService sessionService;

    @GetMapping
    public String listUserBookings(@AuthenticationPrincipal User user, Model model) {
        List<Booking> bookings = bookingService.getAllBookings().stream()
                .filter(b -> b.getUser().getId().equals(user.getId()))
                .toList();
        model.addAttribute("bookings", bookings);
        return "site/bookings/list";
    }

    @GetMapping("/create/{sessionId}")
    public String createBookingForm(@PathVariable Long sessionId, Model model, RedirectAttributes redirectAttributes) {
        Session session = sessionService.getSessionByIdWithDetails(sessionId);
        if (session == null) {
            redirectAttributes.addFlashAttribute("error", "Сеанс не найден");
            return "redirect:/sessions";
        }
        // Debug logging
        System.out.println("Session ID: " + session.getId());
        System.out.println("Movie: " + (session.getMovie() != null ? session.getMovie().getTitle() : "null"));
        System.out.println("Hall: " + (session.getHall() != null ? session.getHall().getName() : "null"));
        System.out.println("StartTime: " + session.getStartTime());
        
        model.addAttribute("movieSession", session);
        return "site/bookings/form";
    }

    @PostMapping("/create/{sessionId}")
    public String createBooking(@PathVariable Long sessionId, @AuthenticationPrincipal User user, RedirectAttributes redirectAttributes) {
        Session session = sessionService.getSessionById(sessionId);
        if (session == null) {
            redirectAttributes.addFlashAttribute("error", "Сеанс не найден");
            return "redirect:/sessions";
        }
        if (user == null) {
            redirectAttributes.addFlashAttribute("error", "Необходимо войти в систему");
            return "redirect:/auth/login";
        }
        Booking booking = new Booking();
        booking.setUser(user);
        booking.setSession(session);
        booking.setBookingDate(LocalDateTime.now());
        booking.setStatus(BookingStatus.PENDING);
        bookingService.saveBooking(booking);
        redirectAttributes.addFlashAttribute("message", "Бронирование создано успешно!");
        return "redirect:/bookings";
    }

    @PostMapping("/{id}/cancel")
    public String cancelBooking(@PathVariable Long id, @AuthenticationPrincipal User user, RedirectAttributes redirectAttributes) {
        Booking booking = bookingService.getBookingById(id);
        if (booking != null && booking.getUser().getId().equals(user.getId())) {
            booking.setStatus(BookingStatus.CANCELLED);
            bookingService.saveBooking(booking);
            redirectAttributes.addFlashAttribute("message", "Бронирование отменено");
        }
        return "redirect:/bookings";
    }
}