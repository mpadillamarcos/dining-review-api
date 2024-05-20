package mpadillamarcos.diningreview.service;

import lombok.RequiredArgsConstructor;
import mpadillamarcos.diningreview.exception.NotFoundException;
import mpadillamarcos.diningreview.exception.UsernameNotAvailableException;
import mpadillamarcos.diningreview.model.UpdateRequest;
import mpadillamarcos.diningreview.model.User;
import mpadillamarcos.diningreview.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static mpadillamarcos.diningreview.model.User.newUser;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository repository;

    public void createNewUser(User request) {
        if (repository.existsById(request.getUsername())) {
            throw new UsernameNotAvailableException("Username " + request.getUsername() + " is already taken");
        }

        repository.save(request);
    }

    public void update(String username, UpdateRequest request) {
        if (!repository.existsById(username)) {
            throw new NotFoundException("Username " + username + " does not exist");
        }

        var updatedUser = newUser()
                .username(username)
                .city(request.getCity())
                .state(request.getState())
                .zipcode(request.getZipcode())
                .peanut(request.getPeanut())
                .egg(request.getEgg())
                .dairy(request.getDairy())
                .build();

        repository.save(updatedUser);
    }

    public Optional<User> find(String username) {
        return Optional.empty();
    }

}
