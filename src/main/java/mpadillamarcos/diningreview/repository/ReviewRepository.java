package mpadillamarcos.diningreview.repository;

import mpadillamarcos.diningreview.model.Review;
import mpadillamarcos.diningreview.model.ReviewState;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ReviewRepository extends CrudRepository<Review, Long> {

    Review save(Review review);

    List<Review> findByState(ReviewState state);

    List<Review> findByStateAndRestaurantId(ReviewState state, Long restaurantId);
}
