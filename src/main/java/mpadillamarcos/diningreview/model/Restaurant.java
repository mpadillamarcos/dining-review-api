package mpadillamarcos.diningreview.model;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;

import static mpadillamarcos.diningreview.utils.Checks.require;
import static mpadillamarcos.diningreview.utils.Checks.requireValidZipcode;

@Data
@Entity
@Table(name = "restaurants")
@Builder(toBuilder = true)
public class Restaurant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "zipcode")
    private Integer zipcode;

    @Column(name = "peanut_score")
    private Float peanut;

    @Column(name = "egg_score")
    private Float egg;

    @Column(name = "dairy_score")
    private Float dairy;

    @Column(name = "total_score")
    private Float total;

    public Restaurant(Long id, String name, Integer zipcode, Float peanut, Float egg, Float dairy, Float total) {
        this.id = id;
        this.name = require("name", name);
        this.zipcode = requireValidZipcode(zipcode);
        this.peanut = peanut;
        this.egg = egg;
        this.dairy = dairy;
        this.total = total;
    }

    public Restaurant() {
    }

    public static RestaurantBuilder newRestaurant() {
        return builder();
    }
}
