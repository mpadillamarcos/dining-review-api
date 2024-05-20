package mpadillamarcos.diningreview.model;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RestaurantRequest {

    @NotNull
    private String name;

    @NotNull
    private Integer zipcode;

    public static RestaurantRequestBuilder restaurantRequestBuilder() {
        return new RestaurantRequestBuilder();
    }
}
