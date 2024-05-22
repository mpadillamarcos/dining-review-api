package mpadillamarcos.diningreview.service;

import mpadillamarcos.diningreview.model.Review;
import mpadillamarcos.diningreview.repository.ReviewRepository;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static mpadillamarcos.diningreview.model.Instances.dummyReviewRequestBuilder;
import static mpadillamarcos.diningreview.model.ReviewState.PENDING;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class ReviewServiceTest {

    @Autowired
    private ReviewRepository repository;

    @Autowired
    private ReviewService service;

    @Nested
    class Submit {
        @Test
        void persists_new_review() {
            var username = "maria123";
            var restaurantId = 7L;
            var reviewRequest = dummyReviewRequestBuilder().peanutScore(4).build();

            var id = service.submit(username, restaurantId, reviewRequest);

            assertThat(repository.findById(id)).get()
                    .returns(id, Review::getId)
                    .returns(restaurantId, Review::getRestaurantId)
                    .returns(username, Review::getUsername)
                    .returns(PENDING, Review::getState)
                    .returns(4, Review::getPeanutScore)
                    .returns(null, Review::getEggScore)
                    .returns(null, Review::getDairyScore)
                    .returns(null, Review::getCommentary);
        }
    }

}