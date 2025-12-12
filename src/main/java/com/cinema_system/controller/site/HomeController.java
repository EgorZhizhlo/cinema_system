package com.cinema_system.controller.site;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String index() {
        return "site/index";
    }

    @GetMapping("/about")
    public String about() {
        return "site/about";
    }
}
