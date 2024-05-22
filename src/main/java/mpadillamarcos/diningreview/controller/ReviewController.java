package mpadillamarcos.diningreview.controller;

import lombok.RequiredArgsConstructor;
import mpadillamarcos.diningreview.model.Review;
import mpadillamarcos.diningreview.model.ReviewRequest;
import mpadillamarcos.diningreview.service.ReviewService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService service;

    @PostMapping("/{username}/{restaurantId}/reviews")
    public void submitReview(
            @PathVariable String username,
            @PathVariable Long restaurantId,
            @RequestBody ReviewRequest request
    ) {
        service.submit(username, restaurantId, request);
    }

    @GetMapping("/admin/pending")
    public List<Review> findPendingReviews() {
        return service.findPendingReviews();
    }
}
