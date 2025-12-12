package com.cinema_system.controller.site;

import com.cinema_system.model.Role;
import com.cinema_system.model.User;
import com.cinema_system.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.time.LocalDateTime;

@Controller
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;

    @GetMapping("/login")
    public String login() {
        return "site/auth/login";
    }

    @GetMapping("/registration")
    public String registrationForm(Model model) {
        model.addAttribute("userForm", new User());
        return "site/auth/registration";
    }

    @PostMapping("/registration")
    public String registerUser(User userForm) {
        userForm.setRole(Role.USER);
        userForm.setCreatedAt(LocalDateTime.now());
        userService.createUser(userForm);
        return "redirect:/login?registered=true";
    }
}