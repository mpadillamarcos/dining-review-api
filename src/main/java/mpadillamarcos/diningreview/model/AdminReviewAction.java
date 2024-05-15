package mpadillamarcos.diningreview.model;

import lombok.Builder;
import lombok.Data;

import static mpadillamarcos.diningreview.utils.Checks.require;

@Data
@Builder(toBuilder = true)
public class AdminReviewAction {

    private Long reviewId;
    private Boolean accepted;

    public AdminReviewAction(Long reviewId, Boolean accepted) {
        this.reviewId = require("reviewId", reviewId);
        this.accepted = require("accepted", accepted);
    }

    public static AdminReviewActionBuilder newAdminReviewAction() {
        return builder().accepted(false);
    }

}
