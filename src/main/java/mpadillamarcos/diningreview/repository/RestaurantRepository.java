package mpadillamarcos.diningreview.repository;

import mpadillamarcos.diningreview.model.Restaurant;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface RestaurantRepository extends CrudRepository<Restaurant, Long> {

    Restaurant save(Restaurant restaurant);

    Optional<Restaurant> findById(Long id);

    List<Restaurant> findByZipcodeAndTotalNotNullOrderByTotalDesc(Integer zipcode);
}
