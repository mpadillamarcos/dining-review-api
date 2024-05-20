package mpadillamarcos.diningreview.model;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;

import static mpadillamarcos.diningreview.utils.Checks.require;

@Data
@Entity
@Table(name = "restaurants")
@Builder(toBuilder = true)
public class Restaurant {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "zipcode")
    private Integer zipcode;

    @Column(name = "peanut-score")
    private Float peanut;

    @Column(name = "egg-score")
    private Float egg;

    @Column(name = "dairy-score")
    private Float dairy;

    @Column(name = "total-score")
    private Float total;

    public Restaurant(Long id, String name, Integer zipcode, Float peanut, Float egg, Float dairy, Float total) {
        this.id = require("id", id);
        this.name = require("name", name);
        this.zipcode = require("zipcode", zipcode);
        this.peanut = peanut;
        this.egg = egg;
        this.dairy = dairy;
        this.total = total;
    }

    public static RestaurantBuilder newRestaurant() {
        return builder();
    }
}
