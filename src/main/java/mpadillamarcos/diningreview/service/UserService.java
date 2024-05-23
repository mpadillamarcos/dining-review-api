package mpadillamarcos.diningreview.service;

import lombok.RequiredArgsConstructor;
import mpadillamarcos.diningreview.exception.NotFoundException;
import mpadillamarcos.diningreview.exception.UsernameNotAvailableException;
import mpadillamarcos.diningreview.model.UserUpdateRequest;
import mpadillamarcos.diningreview.model.User;
import mpadillamarcos.diningreview.model.UserRequest;
import mpadillamarcos.diningreview.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static mpadillamarcos.diningreview.model.User.newUser;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository repository;

    public void createNewUser(UserRequest request) {
        if (repository.existsById(request.getUsername())) {
            throw new UsernameNotAvailableException("Username " + request.getUsername() + " is already taken");
        }
        var user = newUser()
                .username(request.getUsername())
                .city(request.getCity())
                .state(request.getState())
                .zipcode(request.getZipcode())
                .peanut(request.getPeanut())
                .egg(request.getEgg())
                .dairy(request.getDairy())
                .build();
        repository.save(user);
    }

    public void update(String username, UserUpdateRequest request) {
        var user = repository.findById(username)
                .orElseThrow(() -> new NotFoundException("Username " + username + " does not exist"));

        user.setCity(request.getCity());
        user.setState(request.getState());
        user.setZipcode(request.getZipcode());
        user.setPeanut(request.getPeanut());
        user.setEgg(request.getEgg());
        user.setDairy(request.getDairy());

        repository.save(user);
    }

    public Optional<User> find(String username) {
        return repository.findById(username);
    }

}
