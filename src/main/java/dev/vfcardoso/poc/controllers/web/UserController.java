package dev.vfcardoso.poc.controllers.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller("WebUserController")
@RequestMapping("/dashboard/users")
public class UserController {

    @GetMapping("/list")
    public String list(Model model) {
        return "pages/dashboard/user/list";
    }

}
