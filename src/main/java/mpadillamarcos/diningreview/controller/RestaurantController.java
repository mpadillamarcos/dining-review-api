package mpadillamarcos.diningreview.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import mpadillamarcos.diningreview.model.RestaurantRequest;
import mpadillamarcos.diningreview.service.RestaurantService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class RestaurantController {

    private final RestaurantService service;

    @PostMapping("/restaurants")
    public void newRestaurant(@Valid @RequestBody RestaurantRequest request) {
        service.newRestaurant(request);
    }

}
