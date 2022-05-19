package dev.vfcardoso.poc.business.repositories.base;

import dev.vfcardoso.poc.business.models.User;
import dev.vfcardoso.poc.business.repositories.custom.UserRepositoryCustom;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long>, UserRepositoryCustom {
    User findByUsername(String username);
    User save(User user);
}
