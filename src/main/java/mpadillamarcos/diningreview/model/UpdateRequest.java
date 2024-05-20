package mpadillamarcos.diningreview.model;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UpdateRequest {

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

    public static UpdateRequestBuilder updateRequestBuilder() {
        return new UpdateRequestBuilder();
    }
}
