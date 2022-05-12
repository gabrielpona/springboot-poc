package dev.vfcardoso.poc.controllers.web;

import dev.vfcardoso.poc.business.models.User;
import dev.vfcardoso.poc.business.repositories.base.UserRepository;
import dev.vfcardoso.poc.business.valueobjects.SecurityRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller("WebUserController")
@RequestMapping("/dashboard/users")
public class UserController {

    @Autowired
    UserRepository userRepository;

    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("securityRoles", SecurityRole.values());
        //SecurityRole role  =  SecurityRole.USER;
        //role.ordinal();
        //role.name();

        return "pages/dashboard/user/create";
    }

    @GetMapping("/list")
    public String list(Model model) { return "pages/dashboard/user/list"; }


    @PostMapping("/add")
    public String add(@ModelAttribute User user, Model model) {
        try{
            userRepository.save(user);
            model.addAttribute("message", "Usuário Adicionado.");
            return "pages/dashboard/user/list";
        }catch (Exception e){
            model.addAttribute("errorMessage", e.getMessage());
            model.addAttribute("securityRoles", SecurityRole.values());
            return "pages/dashboard/user/create";
        }

    }



}
