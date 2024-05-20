package mpadillamarcos.diningreview.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import mpadillamarcos.diningreview.model.UpdateRequest;
import mpadillamarcos.diningreview.model.User;
import mpadillamarcos.diningreview.service.UserService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService service;

    @PostMapping("/users")
    public User createNewUser(@Valid @RequestBody User request) {
        return service.createNewUser(request);
    }

    @PutMapping("/users/{username}")
    public User updateUser(@PathVariable String username, @NotNull @RequestBody UpdateRequest request) {
        return service.update(username, request);
    }
}