package dev.vfcardoso.poc.controllers.api;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class AuthApiController {

    @RequestMapping("/login")
    public String login() {
        return "pages/auth/login";
    }

    @RequestMapping("/login-error")
    public String loginError(Model model) {
        model.addAttribute("loginError", true);
        return "pages/auth/login";
    }
}
