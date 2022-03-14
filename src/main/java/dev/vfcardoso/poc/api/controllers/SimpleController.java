package dev.vfcardoso.poc.api.controllers;

import dev.vfcardoso.poc.configs.environment.transients.EnvironmentValues;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

@Controller
public class SimpleController {
    @Value("${spring.application.name}") String appName;

    @GetMapping("/home2")
    public String hp(Model model) {
        model.addAttribute("appName", appName);
        return "home";
    }
}
