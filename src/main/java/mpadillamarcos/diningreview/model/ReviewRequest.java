package mpadillamarcos.diningreview.model;

import jakarta.validation.constraints.AssertTrue;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ReviewRequest {

    private Integer peanutScore;
    private Integer eggScore;
    private Integer dairyScore;
    private String commentary;

    public static ReviewRequestBuilder reviewRequestBuilder() {
        return new ReviewRequestBuilder();
    }
}
