package mpadillamarcos.diningreview.repository;

import mpadillamarcos.diningreview.model.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User, String> {

    User save(User user);

    Optional<User> findById(String username);

    boolean existsById(String username);

}
