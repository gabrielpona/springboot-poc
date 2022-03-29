package dev.vfcardoso.poc.api.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DashboardController {

    @GetMapping("/dashboard")
    public String home(Model model) {
        return "pages/dashboard/home";
    }

    @GetMapping("/dashboard/users/list")
    public String list(Model model) {
        return "pages/dashboard/list";
    }

}
