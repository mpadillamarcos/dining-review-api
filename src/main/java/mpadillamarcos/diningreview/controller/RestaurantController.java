package mpadillamarcos.diningreview.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import mpadillamarcos.diningreview.exception.NotFoundException;
import mpadillamarcos.diningreview.model.Restaurant;
import mpadillamarcos.diningreview.model.RestaurantRequest;
import mpadillamarcos.diningreview.service.RestaurantService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

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
        var restaurants = service.findRestaurants(zipcode, allergy);
        if (restaurants.isEmpty()) {
            throw new NotFoundException("No results found");
        }

        return restaurants;
    }

}
