package dev.vfcardoso.poc.business.repositories;

import dev.vfcardoso.poc.business.models.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
    User findByUsername(String username);
    User save(User user);
}
