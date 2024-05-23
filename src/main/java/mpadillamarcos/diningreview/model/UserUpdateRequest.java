package mpadillamarcos.diningreview.model;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserUpdateRequest {

    @NotNull
    private String city;

    @NotNull
    private String state;

    @NotNull
    private Integer zipcode;

    @NotNull
    private Boolean peanut;

    @NotNull
    private Boolean egg;

    @NotNull
    private Boolean dairy;

    public static UserUpdateRequestBuilder userUpdateRequestBuilder() {
        return new UserUpdateRequestBuilder();
    }
}
