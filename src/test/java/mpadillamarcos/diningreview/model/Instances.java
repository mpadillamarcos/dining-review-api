package mpadillamarcos.diningreview.model;

import mpadillamarcos.diningreview.model.User.UserBuilder;

import static mpadillamarcos.diningreview.model.Review.ReviewBuilder;
import static mpadillamarcos.diningreview.model.Review.newReview;
import static mpadillamarcos.diningreview.model.ReviewState.PENDING;
import static mpadillamarcos.diningreview.model.User.newUser;

public class Instances {

    public static ReviewBuilder dummyReview() {
        return newReview()
                .id(123L)
                .restaurant("Bella Trattoria")
                .username("maria123")
                .state(PENDING)
                .peanutScore(3)
                .eggScore(5)
                .diaryScore(4)
                .commentary("Good job");
    }

    public static UserBuilder dummyUser() {
        return newUser()
                .username("maria123")
                .city("San Francisco")
                .state("California")
                .zipcode(94118)
                .peanut(false)
                .egg(false)
                .diary(true);

    }
}
