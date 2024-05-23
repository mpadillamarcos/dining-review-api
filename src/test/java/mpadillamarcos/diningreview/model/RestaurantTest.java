package mpadillamarcos.diningreview.model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;

import static mpadillamarcos.diningreview.model.Instances.dummyRestaurant;
import static mpadillamarcos.diningreview.model.Restaurant.newRestaurant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class RestaurantTest {

    @ParameterizedTest(name = "{0}")
    @MethodSource("restaurantsWithMissingData")
    void requires_mandatory_fields(String field, Restaurant.RestaurantBuilder builder) {
        var exception = assertThrows(IllegalArgumentException.class, builder::build);

        assertThat(exception).hasMessage(field + " must not be null");
    }

    @Test
    void requires_valid_zipcode() {
        var exception = assertThrows(IllegalArgumentException.class, dummyRestaurant().zipcode("123456")::build);

        assertThat(exception).hasMessage("Invalid US zipcode: 123456");
    }

    @Test
    void creates_restaurant_with_builder_values() {
        var id = 1234L;
        var name = "Lolailo";

        var restaurant = newRestaurant()
                .id(id)
                .name(name)
                .zipcode("12345")
                .build();

        assertThat(restaurant)
                .returns(id, Restaurant::getId)
                .returns(name, Restaurant::getName)
                .returns("12345", Restaurant::getZipcode);
    }

    static List<Arguments> restaurantsWithMissingData() {
        return List.of(
                Arguments.arguments("name", dummyRestaurant().name(null)),
                Arguments.arguments("zipcode", dummyRestaurant().zipcode(null))
        );
    }
}