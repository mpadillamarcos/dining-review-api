package mpadillamarcos.diningreview.model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;

import static mpadillamarcos.diningreview.model.Instances.dummyUser;
import static mpadillamarcos.diningreview.model.User.newUser;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class UserTest {

    @ParameterizedTest(name = "{0}")
    @MethodSource("usersWithMissingData")
    void requires_mandatory_fields(String field, User.UserBuilder builder) {
        var exception = assertThrows(IllegalArgumentException.class, builder::build);

        assertThat(exception).hasMessage(field + " must not be null");
    }

    @Test
    void requires_valid_zipcode() {
        var exception = assertThrows(IllegalArgumentException.class, dummyUser().zipcode("456789")::build);

        assertThat(exception).hasMessage("Invalid US zipcode: 456789");
    }

    @Test
    void creates_user_with_builder_values() {
        var username = "Pepe123";
        var city = "Gillette";
        var state = "Wyoming";

        var user = newUser()
                .username(username)
                .city(city)
                .state(state)
                .zipcode("82716")
                .peanut(true)
                .egg(false)
                .dairy(true)
                .build();

        assertThat(user)
                .returns(username, User::getUsername)
                .returns(city, User::getCity)
                .returns(state, User::getState)
                .returns("82716", User::getZipcode)
                .returns(true, User::getPeanut)
                .returns(false, User::getEgg)
                .returns(true, User::getDairy);
    }

    static List<Arguments> usersWithMissingData() {
        return List.of(
                Arguments.arguments("username", dummyUser().username(null)),
                Arguments.arguments("city", dummyUser().city(null)),
                Arguments.arguments("state", dummyUser().state(null)),
                Arguments.arguments("zipcode", dummyUser().zipcode(null)),
                Arguments.arguments("peanut", dummyUser().peanut(null)),
                Arguments.arguments("egg", dummyUser().egg(null)),
                Arguments.arguments("dairy", dummyUser().dairy(null))
        );
    }

}