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

    public List<Restaurant> findRestaurants(String zipcode, String allergy) {
        if (allergy.equals("peanut")) {
            return repository.findByPeanutNotNullAndZipcodeOrderByPeanutDesc(zipcode);
        } else if (allergy.equals("egg")) {
            return repository.findByEggNotNullAndZipcodeOrderByEggDesc(zipcode);
        } else {
            return repository.findByDairyNotNullAndZipcodeOrderByDairyDesc(zipcode);
        }
    }

    public List<Restaurant> findRestaurantsByZipcode(String zipcode) {
        return repository.findByZipcode(zipcode);
    }

    public List<Restaurant> findRestaurantsByAllergy(String allergy) {
        return switch (allergy) {
            case "peanut" -> repository.findByPeanutNotNullOrderByPeanutDesc();
            case "egg" -> repository.findByEggNotNullOrderByEggDesc();
            case "dairy" -> repository.findByDairyNotNullOrderByDairyDesc();
            default -> emptyList();
        };
    }

    public List<Restaurant> findRestaurantsByAllergy() {
        return repository.findAll();
    }
}
