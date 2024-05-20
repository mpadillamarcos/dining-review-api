package mpadillamarcos.diningreview.model;

import mpadillamarcos.diningreview.model.User.UserBuilder;

import static mpadillamarcos.diningreview.model.Restaurant.*;
import static mpadillamarcos.diningreview.model.Review.ReviewBuilder;
import static mpadillamarcos.diningreview.model.Review.newReview;
import static mpadillamarcos.diningreview.model.ReviewState.PENDING;
import static mpadillamarcos.diningreview.model.UpdateRequest.*;
import static mpadillamarcos.diningreview.model.User.newUser;

public class Instances {

    public static RestaurantBuilder dummyRestaurant() {
        return newRestaurant()
                .id(456L)
                .name("Bella Trattoria")
                .zipcode(94118)
                .peanut(3F)
                .egg(4.4F)
                .diary(2.3F)
                .total(3.23F);
    }

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

    public static UpdateRequest.UpdateRequestBuilder dummyUpdateRequestBuilder() {
        return updateRequestBuilder();
    }
}