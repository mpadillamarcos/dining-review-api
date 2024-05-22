package mpadillamarcos.diningreview.controller;

import mpadillamarcos.diningreview.service.ReviewService;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static mpadillamarcos.diningreview.model.Instances.dummyReview;
import static mpadillamarcos.diningreview.model.Instances.dummyReviewRequestBuilder;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
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

    @Nested
    class FindPendingReviews {
        @Test
        void returns_list_of_pending_reviews() throws Exception {
            var review1 = dummyReview().id(1L).restaurantId(2L).username("maria").build();
            var review2 = dummyReview().id(2L).restaurantId(3L).username("pepa").build();

            when(reviewService.findPendingReviews())
                    .thenReturn(List.of(review1, review2));

            mockMvc.perform(get("/admin/pending"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("[0].id", equalTo(1)))
                    .andExpect(jsonPath("[0].restaurantId", equalTo(2)))
                    .andExpect(jsonPath("[0].username", equalTo(review1.getUsername())))
                    .andExpect(jsonPath("[0].state", equalTo("PENDING")))
                    .andExpect(jsonPath("[0].peanutScore", equalTo(review1.getPeanutScore())))
                    .andExpect(jsonPath("[0].eggScore", equalTo(review1.getEggScore())))
                    .andExpect(jsonPath("[0].dairyScore", equalTo(review1.getDairyScore())))
                    .andExpect(jsonPath("[0].commentary", equalTo(review1.getCommentary())))
                    .andExpect(jsonPath("[1].id", equalTo(2)))
                    .andExpect(jsonPath("[1].restaurantId", equalTo(3)))
                    .andExpect(jsonPath("[1].username", equalTo(review2.getUsername())))
                    .andExpect(jsonPath("[1].state", equalTo("PENDING")))
                    .andExpect(jsonPath("[1].peanutScore", equalTo(review2.getPeanutScore())))
                    .andExpect(jsonPath("[1].eggScore", equalTo(review2.getEggScore())))
                    .andExpect(jsonPath("[1].dairyScore", equalTo(review2.getDairyScore())))
                    .andExpect(jsonPath("[1].commentary", equalTo(review2.getCommentary())));
        }
    }

    @Nested
    class Accept {
        @Test
        void returns_ok_when_accepting_review() throws Exception {
            mockMvc.perform(put("/admin/3/accept"))
                    .andExpect(status().isOk());
            verify(reviewService, times(1))
                    .accept(3L);
        }
    }

    @Nested
    class Reject {
        @Test
        void returns_ok_when_rejecting_review() throws Exception {
            mockMvc.perform(put("/admin/3/reject"))
                    .andExpect(status().isOk());
            verify(reviewService, times(1))
                    .reject(3L);
        }
    }

}
