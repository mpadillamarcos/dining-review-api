package mpadillamarcos.diningreview.service;

import mpadillamarcos.diningreview.exception.RestaurantAlreadyExistsException;
import mpadillamarcos.diningreview.model.Restaurant;
import mpadillamarcos.diningreview.repository.RestaurantRepository;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static mpadillamarcos.diningreview.model.Instances.dummyRestaurant;
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
            var restaurant = dummyRestaurantRequestBuilder().name("Palermo").zipcode("45678").build();

            var id = service.newRestaurant(restaurant);

            assertThat(repository.findById(id)).get()
                    .returns("Palermo", Restaurant::getName)
                    .returns("45678", Restaurant::getZipcode);
        }

        @Test
        void throws_exception_when_restaurant_already_exists() {
            var restaurant1 = dummyRestaurantRequestBuilder().name("Pizzeria").zipcode("12345").build();
            service.newRestaurant(restaurant1);

            var restaurant2 = dummyRestaurantRequestBuilder().name("Pizzeria").zipcode("12345").build();
            assertThrows(RestaurantAlreadyExistsException.class, () -> service.newRestaurant(restaurant2));
        }
    }

    @Nested
    class FindRestaurantById {
        @Test
        void returns_restaurant_information() {
            var request = dummyRestaurantRequestBuilder().name("La Toscana").zipcode("40504").build();
            var id = service.newRestaurant(request);
            var restaurant = dummyRestaurant()
                    .id(id)
                    .name("La Toscana")
                    .zipcode("40504")
                    .peanut(null)
                    .egg(null)
                    .dairy(null)
                    .total(null)
                    .build();

            var response = service.findRestaurantById(id);

            assertThat(response).isEqualTo(Optional.of(restaurant));
        }
    }

    @Nested
    class FindRestaurants {
        @Test
        void returns_multiple_restaurants_searching_by_zipcode_and_allergy() {
            var restaurant1 = dummyRestaurant()
                    .id(1L)
                    .name("Max Burger")
                    .zipcode("11122")
                    .peanut(null)
                    .egg(4.4F)
                    .dairy(4.3F)
                    .total(4.35F)
                    .build();
            var restaurant2 = dummyRestaurant()
                    .id(2L)
                    .name("Piper Pizza")
                    .zipcode("11122")
                    .peanut(null)
                    .egg(4.5F)
                    .dairy(null)
                    .total(4.5F)
                    .build();
            repository.save(restaurant1);
            repository.save(restaurant2);

            var response = service.findRestaurants("11122", "egg");

            assertThat(response).containsExactlyElementsOf(List.of(restaurant2, restaurant1));
        }

        @Test
        void returns_an_empty_list_when_none_of_the_restaurants_match_the_search_searching_by_zipcode_and_allergy() {
            var response = service.findRestaurants("11122", "peanut");

            assertThat(response).isEmpty();
        }

        @Test
        void returns_multiple_restaurants_searching_by_zipcode() {
            var restaurant1 = dummyRestaurant()
                    .id(1L)
                    .name("Max Burger")
                    .zipcode("11122")
                    .peanut(null)
                    .egg(4.4F)
                    .dairy(4.3F)
                    .total(4.35F)
                    .build();
            var restaurant2 = dummyRestaurant()
                    .id(2L)
                    .name("Piper Pizza")
                    .zipcode("11122")
                    .peanut(null)
                    .egg(4.5F)
                    .dairy(null)
                    .total(4.5F)
                    .build();
            repository.save(restaurant1);
            repository.save(restaurant2);

            var response = service.findRestaurantsByZipcode("11122");

            assertThat(response).containsExactlyElementsOf(List.of(restaurant1, restaurant2));
        }

        @Test
        void returns_an_empty_list_when_none_of_the_restaurants_match_the_zipcode() {
            var response = service.findRestaurantsByZipcode("10000");

            assertThat(response).isEmpty();
        }

        @Test
        void returns_multiple_restaurants_searching_by_allergy() {
            var restaurant1 = dummyRestaurant()
                    .id(1L)
                    .name("Max Burger")
                    .zipcode("11122")
                    .peanut(null)
                    .egg(4.4F)
                    .dairy(4.3F)
                    .total(4.35F)
                    .build();
            var restaurant2 = dummyRestaurant()
                    .id(2L)
                    .name("Piper Pizza")
                    .zipcode("11123")
                    .peanut(null)
                    .egg(4.5F)
                    .dairy(null)
                    .total(4.5F)
                    .build();
            repository.save(restaurant1);
            repository.save(restaurant2);

            var response = service.findRestaurantsByAllergy("egg");

            assertThat(response).containsExactlyElementsOf(List.of(restaurant2, restaurant1));
        }

        @Test
        void returns_an_empty_list_when_none_of_the_restaurants_match_the_allergy() {
            var response = service.findRestaurantsByAllergy("peanut");

            assertThat(response).isEmpty();
        }
    }

}