package dev.vfcardoso.poc.api.controllers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    @Value("${spring.application.name}") String appName;

    @GetMapping("/")
    public String landing(Model model) {
        model.addAttribute("appName", appName);
        return "pages/public/landing";
    }

    /*
    @GetMapping("/home")
    public String home(Model model) {
        model.addAttribute("appName", appName);
        return "pages/home/home";
    }
     */

}