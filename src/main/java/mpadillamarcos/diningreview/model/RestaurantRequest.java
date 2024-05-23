package mpadillamarcos.diningreview.model;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import static mpadillamarcos.diningreview.utils.Checks.requireValidZipcode;

@Data
@Builder
public class RestaurantRequest {

    @NotNull
    private String name;

    @NotNull
    private String zipcode;

    public RestaurantRequest(String name, String zipcode) {
        this.name = name;
        this.zipcode = requireValidZipcode(zipcode);
    }

    public static RestaurantRequestBuilder restaurantRequestBuilder() {
        return new RestaurantRequestBuilder();
    }
}
