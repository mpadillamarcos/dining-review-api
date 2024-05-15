package mpadillamarcos.diningreview.model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;

import static mpadillamarcos.diningreview.model.AdminReviewAction.newAdminReviewAction;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class AdminReviewActionTest {

    @ParameterizedTest(name = "{0}")
    @MethodSource("adminReviewActionsWithMissingData")
    void requires_mandatory_fields(String field, AdminReviewAction.AdminReviewActionBuilder builder) {
        var exception = assertThrows(IllegalArgumentException.class, builder::build);

        assertThat(exception).hasMessage(field + " must not be null");
    }

    @Test
    void creates_admin_review_action_with_builder_values() {
        var reviewId = 1234L;

        var adminReviewAction = newAdminReviewAction()
                .reviewId(reviewId)
                .accepted(true)
                .build();

        assertThat(adminReviewAction)
                .returns(reviewId, AdminReviewAction::getReviewId)
                .returns(true, AdminReviewAction::getAccepted);
    }

    static List<Arguments> adminReviewActionsWithMissingData() {
        return List.of(
                Arguments.arguments("reviewId", AdminReviewAction.builder().reviewId(null).accepted(false)),
                Arguments.arguments("accepted", AdminReviewAction.builder().reviewId(123L).accepted(null))
        );
    }
}