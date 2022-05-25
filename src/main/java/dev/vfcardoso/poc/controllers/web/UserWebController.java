package dev.vfcardoso.poc.controllers.web;

import dev.vfcardoso.poc.business.models.User;
import dev.vfcardoso.poc.business.repositories.base.UserRepository;
import dev.vfcardoso.poc.business.valueobjects.SecurityRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller
@RequestMapping("/dashboard/users")
public class UserWebController {

    @Autowired
    UserRepository userRepository;

    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("errorMessage2", "Teste");
        model.addAttribute("securityRoles", SecurityRole.values());
        //SecurityRole role  =  SecurityRole.USER;
        //role.ordinal();
        //role.name();

        return "pages/dashboard/user/create";
    }

    @GetMapping("/list")
    public String list(Model model) { return "pages/dashboard/user/list"; }


    @PostMapping("/add")
    public String add(@ModelAttribute User user, RedirectAttributes redirAttrs) {
        try{
            userRepository.save(user);
            redirAttrs.addFlashAttribute("message", "Usuário Adicionado.");
            return "redirect:list";
        }catch (Exception e){
            redirAttrs.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:create";
        }

    }



}
