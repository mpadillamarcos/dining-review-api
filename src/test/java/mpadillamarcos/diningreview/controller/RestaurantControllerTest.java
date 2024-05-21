package mpadillamarcos.diningreview.controller;

import mpadillamarcos.diningreview.service.RestaurantService;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

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

}