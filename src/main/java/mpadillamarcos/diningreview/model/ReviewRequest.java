package mpadillamarcos.diningreview.model;

import lombok.Builder;
import lombok.Data;

import static mpadillamarcos.diningreview.utils.Checks.requireValidScore;

@Data
@Builder
public class ReviewRequest {

    private Integer peanutScore;
    private Integer eggScore;
    private Integer dairyScore;
    private String commentary;


    public ReviewRequest(Integer peanutScore, Integer eggScore, Integer dairyScore, String commentary) {
        this.peanutScore = requireValidScore(peanutScore);
        this.eggScore = requireValidScore(eggScore);
        this.dairyScore = requireValidScore(dairyScore);
        this.commentary = commentary;
    }

    public static ReviewRequestBuilder reviewRequestBuilder() {
        return new ReviewRequestBuilder();
    }
}
