package mpadillamarcos.diningreview.service;

import lombok.RequiredArgsConstructor;
import mpadillamarcos.diningreview.exception.NotFoundException;
import mpadillamarcos.diningreview.exception.UsernameNotAvailableException;
import mpadillamarcos.diningreview.model.UpdateRequest;
import mpadillamarcos.diningreview.model.User;
import mpadillamarcos.diningreview.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

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
