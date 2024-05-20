package mpadillamarcos.diningreview.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import mpadillamarcos.diningreview.exception.NotFoundException;
import mpadillamarcos.diningreview.model.UpdateRequest;
import mpadillamarcos.diningreview.model.User;
import mpadillamarcos.diningreview.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService service;

    @PostMapping("/users")
    public void createNewUser(@Valid @RequestBody User request) {
        service.createNewUser(request);
    }

    @PutMapping("/users/{username}")
    public void updateUser(@PathVariable String username, @Valid @RequestBody UpdateRequest request) {
        service.update(username, request);
    }

    @GetMapping("/users/{username}")
    public User findUser(@PathVariable String username) {
        Optional<User> user = service.find(username);
        if (user.isEmpty()) {
            throw new NotFoundException("Username not found");
        }
        return user.get();
    }
}
