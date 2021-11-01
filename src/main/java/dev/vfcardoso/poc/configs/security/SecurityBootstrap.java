package dev.vfcardoso.poc.configs.security;

import dev.vfcardoso.poc.business.models.User;
import dev.vfcardoso.poc.business.repositories.UserRepository;
import dev.vfcardoso.poc.business.valueobjects.SecurityRole;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class SecurityBootstrap implements ApplicationListener<ApplicationReadyEvent> {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired private UserRepository userRepository;

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        log.info("Verifying if default user exists");
        createUserWithRole("admin", "admin", "admin@vfcardoso.dev", SecurityRole.ADMIN);
    }

    private void createUserWithRole(String username, String password, String email, SecurityRole authority) {
        if (userRepository.findByUsername(username) != null) {
            return;
        }

        User user = new User();
        user.setUsername(username);
        user.setPassword(new BCryptPasswordEncoder().encode(password));
        user.setDateCreate(new Date());
        user.setEnabled(true);
        user.setEmail(email);
        user.setRole(authority);

        userRepository.save(user);
    }
}
