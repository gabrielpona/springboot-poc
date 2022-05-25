package dev.vfcardoso.poc.controllers.api;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SimpleApiController {
    @Value("${spring.application.name}") String appName;

    @GetMapping("/home2")
    public String hp(Model model) {
        model.addAttribute("appName", appName);
        return "home";
    }
}
