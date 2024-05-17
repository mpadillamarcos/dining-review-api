package mpadillamarcos.diningreview.service;

import lombok.RequiredArgsConstructor;
import mpadillamarcos.diningreview.exception.UsernameNotAvailableException;
import mpadillamarcos.diningreview.model.User;
import mpadillamarcos.diningreview.repository.UserRepository;
import org.springframework.stereotype.Service;

import static mpadillamarcos.diningreview.model.User.*;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository repository;

    public User createNewUser(User request) {
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
                .diary(request.getDiary())
                .build();

        repository.save(user);

        return user;
    }

    public User update(User user) {
        return null;
    }

}
