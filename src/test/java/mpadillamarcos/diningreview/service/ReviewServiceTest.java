package mpadillamarcos.diningreview.service;

import mpadillamarcos.diningreview.exception.NotFoundException;
import mpadillamarcos.diningreview.model.Restaurant;
import mpadillamarcos.diningreview.model.Review;
import mpadillamarcos.diningreview.repository.RestaurantRepository;
import mpadillamarcos.diningreview.repository.ReviewRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static mpadillamarcos.diningreview.model.Instances.dummyReview;
import static mpadillamarcos.diningreview.model.Instances.dummyReviewRequestBuilder;
import static mpadillamarcos.diningreview.model.Restaurant.newRestaurant;
import static mpadillamarcos.diningreview.model.Review.newReview;
import static mpadillamarcos.diningreview.model.ReviewState.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class ReviewServiceTest {

    @Autowired
    private ReviewRepository repository;

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private ReviewService service;

    @BeforeEach
    void clearDatabase(@Autowired ReviewRepository reviewRepository) {
        reviewRepository.deleteAll();
    }

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
            var review1 = newReview().restaurantId(156L).username("maria").state(PENDING).build();
            var review2 = newReview().restaurantId(3L).username("pepa").state(REJECTED).build();
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
        void throws_not_found_when_restaurant_does_not_exist() {
            var review = newReview().restaurantId(456L).username("maria").dairyScore(3).build();
            repository.save(review);
            assertThrows(NotFoundException.class, () -> service.accept(review.getId()));
        }

        @Test
        void persists_state_change_to_accepted() {
            var restaurant = restaurantRepository.save(newRestaurant().name("Potato House").zipcode(11111).build());
            var review = newReview().restaurantId(restaurant.getId()).username("maria").build();
            repository.save(review);

            service.accept(review.getId());

            assertThat(repository.findById(review.getId()))
                    .get()
                    .returns(ACCEPTED, Review::getState);
        }

        @Test
        void updates_restaurant_scores() {
            var restaurant = restaurantRepository.save(newRestaurant().name("Potato House").zipcode(11112).build());
            var review1 = newReview()
                    .restaurantId(restaurant.getId())
                    .username("maria1")
                    .peanutScore(4)
                    .eggScore(2)
                    .build();
            var review2 = newReview()
                    .restaurantId(restaurant.getId())
                    .username("maria2")
                    .peanutScore(3)
                    .dairyScore(4)
                    .build();
            repository.save(review1);
            repository.save(review2);

            service.accept(review1.getId());
            service.accept(review2.getId());

            assertThat(restaurantRepository.findById(restaurant.getId())).get()
                    .returns(3.50F, Restaurant::getPeanut)
                    .returns(2.00F, Restaurant::getEgg)
                    .returns(4.00F, Restaurant::getDairy)
                    .returns(3.17F, Restaurant::getTotal);
        }
    }

    @Nested
    class Reject {
        @Test
        void throws_not_found_when_review_does_not_exist() {
            assertThrows(NotFoundException.class, () -> service.reject(456L));
        }

        @Test
        void persists_state_change_to_rejected() {
            var review = dummyReview().id(1L).restaurantId(2L).username("maria").build();
            repository.save(review);

            service.reject(1L);

            assertThat(repository.findById(1L)).get()
                    .returns(REJECTED, Review::getState);
        }
    }
}