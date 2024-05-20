package mpadillamarcos.diningreview.service;

import lombok.RequiredArgsConstructor;
import mpadillamarcos.diningreview.model.RestaurantRequest;
import mpadillamarcos.diningreview.repository.RestaurantRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RestaurantService {

    private final RestaurantRepository repository;

    public void newRestaurant(RestaurantRequest request) {

    }
}
