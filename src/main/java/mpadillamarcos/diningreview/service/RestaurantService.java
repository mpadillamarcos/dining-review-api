package mpadillamarcos.diningreview.service;

import lombok.RequiredArgsConstructor;
import mpadillamarcos.diningreview.exception.RestaurantAlreadyExistsException;
import mpadillamarcos.diningreview.model.Restaurant;
import mpadillamarcos.diningreview.model.RestaurantRequest;
import mpadillamarcos.diningreview.repository.RestaurantRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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
        if (allergy.equals("peanut")) {
            return repository.findByPeanutNotNullAndZipcodeOrderByPeanutDesc(zipcode);
        } else if (allergy.equals("egg")) {
            return repository.findByEggNotNullAndZipcodeOrderByEggDesc(zipcode);
        } else {
            return repository.findByDairyNotNullAndZipcodeOrderByDairyDesc(zipcode);
        }
    }

    public List<Restaurant> findRestaurants(Integer zipcode) {
        return repository.findByZipcode(zipcode);
    }

    public List<Restaurant> findRestaurants(String allergy) {
        if (allergy.equals("peanut")) {
            return repository.findByPeanutNotNullOrderByPeanutDesc();
        } else if (allergy.equals("egg")) {
            return repository.findByEggNotNullOrderByEggDesc();
        } else {
            return repository.findByDairyNotNullOrderByDairyDesc();
        }
    }
}
