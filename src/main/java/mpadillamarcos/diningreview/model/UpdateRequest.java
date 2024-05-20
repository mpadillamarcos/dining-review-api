package mpadillamarcos.diningreview.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UpdateRequest {

    private String city;
    private String state;
    private Integer zipcode;
    private Boolean peanut;
    private Boolean egg;
    private Boolean dairy;

    public static UpdateRequestBuilder updateRequestBuilder() {
        return new UpdateRequestBuilder();
    }
}
