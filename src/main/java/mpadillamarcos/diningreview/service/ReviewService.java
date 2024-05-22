package mpadillamarcos.diningreview.service;

import lombok.RequiredArgsConstructor;
import mpadillamarcos.diningreview.exception.NotFoundException;
import mpadillamarcos.diningreview.model.Review;
import mpadillamarcos.diningreview.model.ReviewRequest;
import mpadillamarcos.diningreview.repository.ReviewRepository;
import org.springframework.stereotype.Service;

import java.util.List;

import static mpadillamarcos.diningreview.model.Review.newReview;
import static mpadillamarcos.diningreview.model.ReviewState.ACCEPTED;
import static mpadillamarcos.diningreview.model.ReviewState.PENDING;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository repository;

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
                .orElseThrow(() -> new NotFoundException("The reivew with id " + reviewId + " does not exist"));

        review.setState(ACCEPTED);

        repository.save(review);
    }
}
