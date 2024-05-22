package mpadillamarcos.diningreview.controller;

import mpadillamarcos.diningreview.model.ReviewRequest;
import mpadillamarcos.diningreview.service.ReviewService;
import org.apache.coyote.BadRequestException;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static mpadillamarcos.diningreview.model.Instances.dummyReviewRequestBuilder;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(controllers = ReviewController.class)
class ReviewControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ReviewService reviewService;

    @Nested
    class SubmitReview {
        @Test
        void returns_bad_request_when_body_is_invalid() throws Exception {
            mockMvc.perform(post("/maria123/9/reviews")
                            .content("{\"peanutScore\": \"bad\"}")
                            .contentType(APPLICATION_JSON))
                    .andExpect(status().isBadRequest());
        }

        @Test
        void returns_ok_when_body_is_valid() throws Exception {
            String requestBody = """
                    {
                        "peanutScore": 4,
                        "commentary": "Awesome"
                    }
                    """;
            mockMvc.perform(post("/maria123/9/reviews")
                            .content(requestBody)
                            .contentType(APPLICATION_JSON))
                    .andExpect(status().isOk());
            verify(reviewService, times(1))
                    .submit("maria123", 9L, dummyReviewRequestBuilder().peanutScore(4).commentary("Awesome").build());
        }
    }

}
