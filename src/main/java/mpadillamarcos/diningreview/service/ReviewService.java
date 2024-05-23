package mpadillamarcos.diningreview.service;

import lombok.RequiredArgsConstructor;
import mpadillamarcos.diningreview.exception.NotFoundException;
import mpadillamarcos.diningreview.model.Restaurant;
import mpadillamarcos.diningreview.model.Review;
import mpadillamarcos.diningreview.model.ReviewRequest;
import mpadillamarcos.diningreview.repository.RestaurantRepository;
import mpadillamarcos.diningreview.repository.ReviewRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Locale;

import static mpadillamarcos.diningreview.model.Review.newReview;
import static mpadillamarcos.diningreview.model.ReviewState.*;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository repository;
    private final RestaurantRepository restaurantRepository;

    public Long submit(String username, Long restaurantId, ReviewRequest request) {
        var review = newReview()
                .restaurantId(restaurantId)
                .username(username)
                .state(PENDING)
                .peanutScore(request.getPeanutScore())
                .eggScore(request.getEggScore())
                .dairyScore(request.getDairyScore())
                .commentary(request.getCommentary())
                .build();

        var insertedReview = repository.save(review);

        return insertedReview.getId();
    }

    public List<Review> findPendingReviews() {
        return repository.findByState(PENDING);
    }

    public void accept(Long reviewId) {
        var review = repository.findById(reviewId)
                .orElseThrow(() -> new NotFoundException("The review with id " + reviewId + " does not exist"));
        var restaurantId = review.getRestaurantId();
        var restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new NotFoundException("The restaurant with id " + restaurantId + " does not exist"));

        review.setState(ACCEPTED);

        var peanutReviews = findAllAcceptedPeanutReviews(restaurantId);
        var eggReviews = findAllAcceptedEggReviews(restaurantId);
        var dairyReviews = findAllAcceptedDairyReviews(restaurantId);

        if (review.getPeanutScore() != null) {
            restaurant.setPeanut(calculateNewPeanutScore(peanutReviews, restaurant, review));
        }
        if (review.getEggScore() != null) {
            restaurant.setEgg(calculateNewEggScore(eggReviews, restaurant, review));
        }
        if (review.getDairyScore() != null) {
            restaurant.setDairy(calculateNewDairyScore(dairyReviews, restaurant, review));
        }

        calculateTotalScore(restaurant);

        repository.save(review);
        restaurantRepository.save(restaurant);
    }

    private Float calculateNewPeanutScore(List<Review> reviews, Restaurant restaurant, Review newReview) {
        if (restaurant.getPeanut() == null) {
            return newReview.getPeanutScore().floatValue();
        }
        return (restaurant.getPeanut() * reviews.size() + newReview.getPeanutScore()) / (reviews.size() + 1);
    }

    private Float calculateNewEggScore(List<Review> reviews, Restaurant restaurant, Review newReview) {
        if (restaurant.getEgg() == null) {
            return newReview.getEggScore().floatValue();
        }
        return (restaurant.getEgg() * reviews.size() + newReview.getEggScore()) / (reviews.size() + 1);
    }

    private Float calculateNewDairyScore(List<Review> reviews, Restaurant restaurant, Review newReview) {
        if (restaurant.getDairy() == null) {
            return newReview.getDairyScore().floatValue();
        }
        return (restaurant.getDairy() * reviews.size() + newReview.getDairyScore()) / (reviews.size() + 1);
    }

    private void calculateTotalScore(Restaurant restaurant) {
        Float peanut = restaurant.getPeanut();
        Float egg = restaurant.getEgg();
        Float dairy = restaurant.getDairy();

        int count = 0;
        double sum = 0.0;

        if (peanut != null) {
            sum += peanut;
            count++;
        }

        if (egg != null) {
            sum += egg;
            count++;
        }

        if (dairy != null) {
            sum += dairy;
            count++;
        }

        double average = sum / count;

        float roundedAverage = Float.parseFloat(String.format(Locale.ENGLISH, "%.2f", average));
        restaurant.setTotal(roundedAverage);
    }

    private List<Review> findAllAcceptedPeanutReviews(Long restaurantId) {
        return repository.findByStateAndRestaurantIdAndPeanutScoreNotNull(ACCEPTED, restaurantId);
    }

    private List<Review> findAllAcceptedEggReviews(Long restaurantId) {
        return repository.findByStateAndRestaurantIdAndPeanutScoreNotNull(ACCEPTED, restaurantId);
    }

    private List<Review> findAllAcceptedDairyReviews(Long restaurantId) {
        return repository.findByStateAndRestaurantIdAndPeanutScoreNotNull(ACCEPTED, restaurantId);
    }

    public void reject(Long reviewId) {
        var review = repository.findById(reviewId)
                .orElseThrow(() -> new NotFoundException("The review with id " + reviewId + " does not exist"));

        review.setState(REJECTED);

        repository.save(review);
    }

}
