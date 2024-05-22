package mpadillamarcos.diningreview.service;

import mpadillamarcos.diningreview.exception.NotFoundException;
import mpadillamarcos.diningreview.model.Review;
import mpadillamarcos.diningreview.repository.ReviewRepository;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static mpadillamarcos.diningreview.model.Instances.dummyReview;
import static mpadillamarcos.diningreview.model.Instances.dummyReviewRequestBuilder;
import static mpadillamarcos.diningreview.model.ReviewState.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

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

    @Nested
    class FindPendingReviews {
        @Test
        void returns_all_the_pending_reviews() {
            var review1 = dummyReview().id(1L).restaurantId(2L).username("maria").state(PENDING).build();
            var review2 = dummyReview().id(2L).restaurantId(3L).username("pepa").state(REJECTED).build();
            repository.save(review1);
            repository.save(review2);

            assertThat(service.findPendingReviews())
                    .containsExactlyElementsOf(List.of(review1));
        }
    }

    @Nested
    class Accept {
        @Test
        void throws_not_found_when_review_does_not_exist() {
            assertThrows(NotFoundException.class, () -> service.accept(456L));
        }

        @Test
        void persists_state_change_to_accepted() {
            var review = dummyReview().id(1L).restaurantId(2L).username("maria").build();
            repository.save(review);

            service.accept(1L);

            assertThat(repository.findById(1L))
                    .get()
                    .returns(ACCEPTED, Review::getState);
        }
    }
}