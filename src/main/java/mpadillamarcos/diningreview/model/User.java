package mpadillamarcos.diningreview.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import static mpadillamarcos.diningreview.utils.Checks.require;
import static mpadillamarcos.diningreview.utils.Checks.requireValidZipcode;

@Data
@Entity
@Table(name = "users")
@Builder(toBuilder = true)
public class User {

    @Id
    @NotNull
    private String username;

    @Column(name = "city")
    @NotNull
    private String city;

    @Column(name = "state")
    @NotNull
    private String state;

    @Column(name = "zipcode")
    @NotNull
    private String zipcode;

    @Column(name = "peanut")
    @NotNull
    private Boolean peanut;

    @Column(name = "egg")
    @NotNull
    private Boolean egg;

    @Column(name = "dairy")
    @NotNull
    private Boolean dairy;

    public User() {
    }

    public User(String username, String city, String state, String zipcode, Boolean peanut, Boolean egg, Boolean dairy) {
        this.username = require("username", username);
        this.city = require("city", city);
        this.state = require("state", state);
        this.zipcode = requireValidZipcode(zipcode);
        this.peanut = require("peanut", peanut);
        this.egg = require("egg", egg);
        this.dairy = require("dairy", dairy);
    }


    public static UserBuilder newUser() {
        return builder();
    }
}
