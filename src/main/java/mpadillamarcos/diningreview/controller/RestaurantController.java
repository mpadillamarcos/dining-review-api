package mpadillamarcos.diningreview.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mpadillamarcos.diningreview.exception.NotFoundException;
import mpadillamarcos.diningreview.model.Restaurant;
import mpadillamarcos.diningreview.model.RestaurantRequest;
import mpadillamarcos.diningreview.service.RestaurantService;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequiredArgsConstructor
public class RestaurantController {

    private final RestaurantService service;

    @PostMapping("/restaurants")
    public void newRestaurant(@Valid @RequestBody RestaurantRequest request) {
        service.newRestaurant(request);
    }

    @GetMapping("/restaurants/{id}")
    public Restaurant findRestaurant(@PathVariable Long id) {
        Optional<Restaurant> restaurant = service.findRestaurantById(id);
        if (restaurant.isEmpty()) {
            throw new NotFoundException("Restaurant not found");
        }

        return restaurant.get();
    }

    @GetMapping("/restaurants")
    public List<Restaurant> findWithQueryParams(
            @RequestParam(required = false) Integer zipcode,
            @RequestParam(required = false) String allergy
    ) {
        if (zipcode != null && allergy != null) {
            return service.findRestaurants(zipcode, allergy);
        }
        if (allergy != null) {
            return service.findRestaurants(allergy);
        }
        if (zipcode != null) {
            return service.findRestaurants(zipcode);
        }
        return service.findRestaurants();
    }
}
