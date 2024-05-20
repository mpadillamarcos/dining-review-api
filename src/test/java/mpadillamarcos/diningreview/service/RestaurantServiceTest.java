package mpadillamarcos.diningreview.service;

import mpadillamarcos.diningreview.exception.RestaurantAlreadyExistsException;
import mpadillamarcos.diningreview.model.Restaurant;
import mpadillamarcos.diningreview.repository.RestaurantRepository;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static mpadillamarcos.diningreview.model.Instances.dummyRestaurantRequestBuilder;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class RestaurantServiceTest {

    @Autowired
    private RestaurantRepository repository;

    @Autowired
    private RestaurantService service;

    @Nested
    class NewRestaurant {
        @Test
        void persists_new_restaurant() {
            var restaurant = dummyRestaurantRequestBuilder().name("Palermo").zipcode(45678).build();

            var id = service.newRestaurant(restaurant);

            assertThat(repository.findById(id)).get()
                    .returns("Palermo", Restaurant::getName)
                    .returns(45678, Restaurant::getZipcode);
        }

        @Test
        void throws_exception_when_restaurant_already_exists() {
            var restaurant1 = dummyRestaurantRequestBuilder().name("Pizzeria").zipcode(12345).build();
            service.newRestaurant(restaurant1);

            var restaurant2 = dummyRestaurantRequestBuilder().name("Pizzeria").zipcode(12345).build();
            assertThrows(RestaurantAlreadyExistsException.class, () -> service.newRestaurant(restaurant2));
        }
    }

}