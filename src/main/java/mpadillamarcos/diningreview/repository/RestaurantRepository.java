package mpadillamarcos.diningreview.repository;

import mpadillamarcos.diningreview.model.Restaurant;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface RestaurantRepository extends CrudRepository<Restaurant, Long> {

    Restaurant save(Restaurant restaurant);

    Optional<Restaurant> findById(Long id);

    List<Restaurant> findAll();

    List<Restaurant> findByPeanutNotNullAndZipcodeOrderByPeanutDesc(String zipcode);

    List<Restaurant> findByEggNotNullAndZipcodeOrderByEggDesc(String zipcode);

    List<Restaurant> findByDairyNotNullAndZipcodeOrderByDairyDesc(String zipcode);

    List<Restaurant> findByPeanutNotNullOrderByPeanutDesc();

    List<Restaurant> findByEggNotNullOrderByEggDesc();

    List<Restaurant> findByDairyNotNullOrderByDairyDesc();

    List<Restaurant> findByZipcode(String zipcode);

    Optional<Restaurant> findByZipcodeAndName(String zipcode, String name);
}
