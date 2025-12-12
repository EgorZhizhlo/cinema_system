package com.cinema_system.controller.admin;

import com.cinema_system.model.Booking;
import com.cinema_system.model.BookingStatus;
import com.cinema_system.service.BookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin/bookings")
@RequiredArgsConstructor
public class AdminBookingController {
    private final BookingService bookingService;

    @GetMapping
    public String listBookings(Model model) {
        model.addAttribute("bookings", bookingService.getAllBookings());
        return "admin/bookings/list";
    }

    @GetMapping("/{id}")
    public String viewBooking(@PathVariable Long id, Model model) {
        Booking booking = bookingService.getBookingById(id);
        if (booking == null) {
            return "redirect:/admin/bookings";
        }
        model.addAttribute("booking", booking);
        return "admin/bookings/view";
    }

    @PostMapping("/{id}/confirm")
    public String confirmBooking(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        Booking booking = bookingService.getBookingById(id);
        if (booking != null) {
            booking.setStatus(BookingStatus.CONFIRMED);
            bookingService.saveBooking(booking);
            redirectAttributes.addFlashAttribute("message", "Бронирование подтверждено");
        }
        return "redirect:/admin/bookings";
    }

    @PostMapping("/{id}/cancel")
    public String cancelBooking(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        Booking booking = bookingService.getBookingById(id);
        if (booking != null) {
            booking.setStatus(BookingStatus.CANCELLED);
            bookingService.saveBooking(booking);
            redirectAttributes.addFlashAttribute("message", "Бронирование отменено");
        }
        return "redirect:/admin/bookings";
    }

    @PostMapping("/{id}/delete")
    public String deleteBooking(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        bookingService.deleteBooking(id);
        redirectAttributes.addFlashAttribute("message", "Бронирование удалено");
        return "redirect:/admin/bookings";
    }
}