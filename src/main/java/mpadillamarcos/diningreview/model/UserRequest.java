package mpadillamarcos.diningreview.model;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import static mpadillamarcos.diningreview.utils.Checks.requireValidZipcode;

@Data
@Builder
public class UserRequest {

    @NotNull
    private String username;
    @NotNull
    private String city;
    @NotNull
    private String state;
    @NotNull
    private String zipcode;
    @NotNull
    private Boolean peanut;
    @NotNull
    private Boolean egg;
    @NotNull
    private Boolean dairy;

    public UserRequest(String username, String city, String state, String zipcode, Boolean peanut, Boolean egg, Boolean dairy) {
        this.username = username;
        this.city = city;
        this.state = state;
        this.zipcode = requireValidZipcode(zipcode);
        this.peanut = peanut;
        this.egg = egg;
        this.dairy = dairy;
    }

    public static UserRequestBuilder userRequestBuilder() {
        return new UserRequestBuilder();
    }
}
