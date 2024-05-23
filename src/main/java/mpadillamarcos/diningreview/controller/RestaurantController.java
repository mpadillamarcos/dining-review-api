package mpadillamarcos.diningreview.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mpadillamarcos.diningreview.exception.NotFoundException;
import mpadillamarcos.diningreview.model.Restaurant;
import mpadillamarcos.diningreview.model.RestaurantRequest;
import mpadillamarcos.diningreview.service.RestaurantService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static java.lang.Integer.parseInt;

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
        return service.findRestaurantById(id)
                .orElseThrow(() -> new NotFoundException("Restaurant not found"));
    }

    @GetMapping("/restaurants")
    public List<Restaurant> findWithQueryParams(
            @Valid @Pattern(regexp = "\\d{5}") @RequestParam(required = false) String zipcode,
            @RequestParam(required = false) String allergy
    ) {
        if (zipcode != null && allergy != null) {
            return service.findRestaurants(parseInt(zipcode), allergy);
        }
        if (allergy != null) {
            return service.findRestaurants(allergy);
        }
        if (zipcode != null) {
            return service.findRestaurants(parseInt(zipcode));
        }
        return service.findRestaurants();
    }
}
