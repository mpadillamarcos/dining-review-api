package mpadillamarcos.diningreview.service;

import lombok.RequiredArgsConstructor;
import mpadillamarcos.diningreview.exception.RestaurantAlreadyExistsException;
import mpadillamarcos.diningreview.model.Restaurant;
import mpadillamarcos.diningreview.model.RestaurantRequest;
import mpadillamarcos.diningreview.repository.RestaurantRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static java.util.Collections.emptyList;

@Service
@RequiredArgsConstructor
public class RestaurantService {

    private final RestaurantRepository repository;

    public Long newRestaurant(RestaurantRequest request) {
        var oldRestaurant = repository.findByZipcodeAndName(request.getZipcode(), request.getName());
        if (oldRestaurant.isPresent()) {
            throw new RestaurantAlreadyExistsException("This restaurant already exists with id " + oldRestaurant.get().getId());
        }

        var restaurant = Restaurant.newRestaurant()
                .name(request.getName())
                .zipcode(request.getZipcode())
                .build();

        var insertedRestaurant = repository.save(restaurant);

        return insertedRestaurant.getId();
    }

    public Optional<Restaurant> findRestaurantById(Long id) {
        return repository.findById(id);
    }

    public List<Restaurant> findRestaurants(Integer zipcode, String allergy) {
        return emptyList();
    }

    public List<Restaurant> findRestaurants(Integer zipcode) {
        return emptyList();
    }

    public List<Restaurant> findRestaurants(String allergy) {
        return emptyList();
    }
}
