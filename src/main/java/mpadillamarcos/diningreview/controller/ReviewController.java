package mpadillamarcos.diningreview.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import mpadillamarcos.diningreview.model.ReviewRequest;
import mpadillamarcos.diningreview.service.ReviewService;
import org.apache.coyote.BadRequestException;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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
}
