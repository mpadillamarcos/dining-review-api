package mpadillamarcos.diningreview.model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;

import static mpadillamarcos.diningreview.model.Instances.dummyReview;
import static mpadillamarcos.diningreview.model.Review.newReview;
import static mpadillamarcos.diningreview.model.ReviewState.ACCEPTED;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ReviewTest {

    @ParameterizedTest(name = "{0}")
    @MethodSource("reviewsWithMissingData")
    void requires_mandatory_fields(String field, Review.ReviewBuilder builder) {
        var exception = assertThrows(IllegalArgumentException.class, builder::build);

        assertThat(exception).hasMessage(field + " must not be null");
    }

    @Test
    void creates_review_with_builder_values() {
        var id = 1234L;
        var restaurantId = 7L;
        var username = "Pepe123";

        var review = newReview()
                .id(id)
                .restaurantId(restaurantId)
                .username(username)
                .state(ACCEPTED)
                .eggScore(1)
                .peanutScore(2)
                .build();

        assertThat(review)
                .returns(id, Review::getId)
                .returns(restaurantId, Review::getRestaurantId)
                .returns(username, Review::getUsername)
                .returns(ACCEPTED, Review::getState)
                .returns(1, Review::getEggScore)
                .returns(2, Review::getPeanutScore);
    }

    static List<Arguments> reviewsWithMissingData() {
        return List.of(
                Arguments.arguments("restaurantId", dummyReview().restaurantId(null)),
                Arguments.arguments("username", dummyReview().username(null)),
                Arguments.arguments("state", dummyReview().state(null))
        );
    }

}