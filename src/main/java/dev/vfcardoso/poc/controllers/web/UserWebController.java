package dev.vfcardoso.poc.controllers.web;

import dev.vfcardoso.poc.business.models.User;
import dev.vfcardoso.poc.business.repositories.base.UserRepository;
import dev.vfcardoso.poc.business.valueobjects.SecurityRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;


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
        return "pages/dashboard/user/create";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Long id,Model model,RedirectAttributes redirAttrs) {
        try{
            Optional<User> user = userRepository.findById(id);
            if(user.isEmpty()){
                throw new Exception("Usuário não localizado");
            }
            model.addAttribute("user", user.isPresent()?user.get():null);
            model.addAttribute("securityRoles", SecurityRole.values());
        }catch(Exception e){
            redirAttrs.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:list";
        }
        return "pages/dashboard/user/edit";
    }

    @GetMapping("/list")
    public String list(Model model) { return "pages/dashboard/user/list"; }

    @PostMapping("/add")
    public String add(@ModelAttribute User user, RedirectAttributes redirAttrs) {
        try{
            user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
            user.setEnabled(true);
            userRepository.save(user);
            redirAttrs.addFlashAttribute("message", "Usuário Adicionado.");
            return "redirect:list";
        }catch (Exception e){
            redirAttrs.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:create";
        }
    }

    @PostMapping("/update")
    public String update(@ModelAttribute User user, RedirectAttributes redirAttrs) {
        Optional<User> u = userRepository.findById(user.getId());
        try{
            if(u.isEmpty()){
                throw new Exception("Usuário não localizado");
            }
            user.setPassword(u.get().getPassword());
            user.setEnabled(u.get().getEnabled());
            userRepository.save(user);
            redirAttrs.addFlashAttribute("message", "Usuário Atualizado.");
            return "redirect:list";
        }catch (Exception e){
            redirAttrs.addFlashAttribute("errorMessage", e.getMessage());
            String url = u.isEmpty()?"list":"edit/".concat(Long.toString(u.get().getId()));
            return "redirect:".concat(url);
        }
    }

}
