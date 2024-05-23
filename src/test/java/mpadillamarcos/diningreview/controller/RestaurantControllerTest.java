package mpadillamarcos.diningreview.controller;

import mpadillamarcos.diningreview.service.RestaurantService;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static java.util.Collections.emptyList;
import static mpadillamarcos.diningreview.model.Instances.dummyRestaurant;
import static mpadillamarcos.diningreview.model.Instances.dummyRestaurantRequestBuilder;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = RestaurantController.class)
class RestaurantControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private RestaurantService restaurantService;

    @Nested
    class NewRestaurant {
        @Test
        void returns_bad_request_when_body_is_null() throws Exception {
            mockMvc.perform(post("/restaurants")
                            .content("{}")
                            .contentType(APPLICATION_JSON))
                    .andExpect(status().isBadRequest());
        }

        @Test
        void returns_bad_request_when_body_is_not_complete() throws Exception {
            String requestBody = """
                    {
                        "zipcode": 12345
                    }
                    """;

            mockMvc.perform(post("/restaurants")
                            .content(requestBody)
                            .contentType(APPLICATION_JSON))
                    .andExpect(status().isBadRequest());
        }

        @Test
        void returns_bad_request_when_zipcode_is_not_valid() throws Exception {
            String requestBody = """
                    {
                        "name": "La Trattoria",
                        "zipcode": 12345678
                    }
                    """;

            mockMvc.perform(post(
                            "/restaurants")
                            .content(requestBody)
                            .contentType(APPLICATION_JSON))
                    .andExpect(status().isBadRequest());
        }

        @Test
        void returns_ok_when_all_required_parameters_are_valid() throws Exception {
            String requestBody = """
                    {
                        "name": "La Trattoria",
                        "zipcode": 12345
                    }
                    """;

            mockMvc.perform(post(
                            "/restaurants")
                            .content(requestBody)
                            .contentType(APPLICATION_JSON))
                    .andExpect(status().isOk());

            verify(restaurantService, times(1))
                    .newRestaurant(dummyRestaurantRequestBuilder().name("La Trattoria").zipcode(12345).build());
        }
    }

    @Nested
    class FindRestaurant {
        @Test
        void returns_restaurant_information_when_it_exists() throws Exception {
            var restaurant = dummyRestaurant().peanut(null).egg(null).dairy(null).total(null).build();

            when(restaurantService.findRestaurantById(restaurant.getId()))
                    .thenReturn(Optional.of(restaurant));

            mockMvc.perform(get("/restaurants/{id}", restaurant.getId()))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.id", equalTo(restaurant.getId().intValue())))
                    .andExpect(jsonPath("$.name", equalTo(restaurant.getName())))
                    .andExpect(jsonPath("$.zipcode", equalTo(restaurant.getZipcode())))
                    .andExpect(jsonPath("$.peanut").doesNotHaveJsonPath())
                    .andExpect(jsonPath("$.egg").doesNotHaveJsonPath())
                    .andExpect(jsonPath("$.dairy").doesNotHaveJsonPath())
                    .andExpect(jsonPath("$.total").doesNotHaveJsonPath());
        }

        @Test
        void returns_not_found_when_restaurant_id_does_not_exist() throws Exception {
            var restaurant = dummyRestaurant().peanut(null).egg(null).dairy(null).total(null).build();
            var id = restaurant.getId();

            when(restaurantService.findRestaurantById(id))
                    .thenReturn(Optional.empty());

            mockMvc.perform(get("/restaurants/{id}", id))
                    .andExpect(status().isNotFound())
                    .andExpect(jsonPath("$.message", equalTo("Restaurant not found")));

        }
    }

    @Nested
    class FindWithQueryParams {
        @Test
        void returns_restaurants_information_when_they_match_with_the_query_params() throws Exception {
            var restaurant1 = dummyRestaurant()
                    .id(1L)
                    .name("Max Burger")
                    .zipcode(19915)
                    .peanut(4.2F)
                    .egg(4.4F)
                    .dairy(4.3F)
                    .total(4.3F)
                    .build();
            var restaurant2 = dummyRestaurant()
                    .id(2L)
                    .name("Piper Pizza")
                    .zipcode(19915)
                    .peanut(3.2F)
                    .egg(4.5F)
                    .dairy(3.3F)
                    .total(3.67F)
                    .build();

            when(restaurantService.findRestaurants(19915, "dairy"))
                    .thenReturn(List.of(restaurant1, restaurant2));

            mockMvc.perform(get("/restaurants?zipcode=19915&allergy=dairy"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$[0].id", equalTo(restaurant1.getId().intValue())))
                    .andExpect(jsonPath("$[0].name", equalTo(restaurant1.getName())))
                    .andExpect(jsonPath("$[0].zipcode", equalTo(restaurant1.getZipcode())))
                    .andExpect(jsonPath("$[0].peanut", equalTo(4.2)))
                    .andExpect(jsonPath("$[0].egg", equalTo(4.4)))
                    .andExpect(jsonPath("$[0].dairy", equalTo(4.3)))
                    .andExpect(jsonPath("$[0].total", equalTo(4.3)))
                    .andExpect(jsonPath("$[1].id", equalTo(restaurant2.getId().intValue())))
                    .andExpect(jsonPath("$[1].name", equalTo(restaurant2.getName())))
                    .andExpect(jsonPath("$[1].zipcode", equalTo(restaurant2.getZipcode())))
                    .andExpect(jsonPath("$[1].peanut", equalTo(3.2)))
                    .andExpect(jsonPath("$[1].egg", equalTo(4.5)))
                    .andExpect(jsonPath("$[1].dairy", equalTo(3.3)))
                    .andExpect(jsonPath("$[1].total", equalTo(3.67)));

        }

        @Test
        void returns_not_found_when_none_of_the_restaurants_match_the_query_params() throws Exception {
            when(restaurantService.findRestaurants(19915, "peanut"))
                    .thenReturn(emptyList());

            mockMvc.perform(get("/restaurants?zipcode=19915&allergy=peanut"))
                    .andExpect(status().isNotFound())
                    .andExpect(jsonPath("$.message", equalTo("No results found")));
        }
    }

}