package mpadillamarcos.diningreview.model;

import mpadillamarcos.diningreview.model.User.UserBuilder;

import static mpadillamarcos.diningreview.model.Restaurant.RestaurantBuilder;
import static mpadillamarcos.diningreview.model.Restaurant.newRestaurant;
import static mpadillamarcos.diningreview.model.RestaurantRequest.restaurantRequestBuilder;
import static mpadillamarcos.diningreview.model.Review.ReviewBuilder;
import static mpadillamarcos.diningreview.model.Review.newReview;
import static mpadillamarcos.diningreview.model.ReviewRequest.reviewRequestBuilder;
import static mpadillamarcos.diningreview.model.ReviewState.PENDING;
import static mpadillamarcos.diningreview.model.User.newUser;
import static mpadillamarcos.diningreview.model.UserRequest.UserRequestBuilder;
import static mpadillamarcos.diningreview.model.UserRequest.userRequestBuilder;
import static mpadillamarcos.diningreview.model.UserUpdateRequest.userUpdateRequestBuilder;

public class Instances {

    public static RestaurantBuilder dummyRestaurant() {
        return newRestaurant()
                .id(456L)
                .name("Bella Trattoria")
                .zipcode("94118")
                .peanut(3F)
                .egg(4.4F)
                .dairy(2.3F)
                .total(3.23F);
    }

    public static ReviewBuilder dummyReview() {
        return newReview()
                .id(123L)
                .restaurantId(9L)
                .username("maria123")
                .state(PENDING)
                .peanutScore(3)
                .eggScore(5)
                .dairyScore(4)
                .commentary("Good job");
    }

    public static UserBuilder dummyUser() {
        return newUser()
                .username("maria123")
                .city("San Francisco")
                .state("California")
                .zipcode("94118")
                .peanut(false)
                .egg(false)
                .dairy(true);

    }

    public static UserUpdateRequest.UserUpdateRequestBuilder dummyUpdateRequestBuilder() {
        return userUpdateRequestBuilder();
    }

    public static UserRequestBuilder dummyUserRequestBuilder() {
        return userRequestBuilder();
    }

    public static RestaurantRequest.RestaurantRequestBuilder dummyRestaurantRequestBuilder() {
        return restaurantRequestBuilder();
    }

    public static ReviewRequest.ReviewRequestBuilder dummyReviewRequestBuilder() {
        return reviewRequestBuilder();
    }
}
