package mpadillamarcos.diningreview.service;

import lombok.RequiredArgsConstructor;
import mpadillamarcos.diningreview.model.ReviewRequest;
import mpadillamarcos.diningreview.repository.ReviewRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository repository;

    public void submit(String username, Long restaurantId, ReviewRequest request) {

    }
}
