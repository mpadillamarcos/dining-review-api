package mpadillamarcos.diningreview.model;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;

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

    @Column(name = "peanut-score")
    private Float peanut;

    @Column(name = "egg-score")
    private Float egg;

    @Column(name = "diary-score")
    private Float diary;

    public Restaurant(Long id, String name, Float peanut, Float egg, Float diary) {
        this.id = require("id", id);
        this.name = require("name", name);
        this.peanut = peanut;
        this.egg = egg;
        this.diary = diary;
    }

    public static RestaurantBuilder newRestaurant() {
        return builder();
    }

    private static <T> T require(String name, T value) {
        if (value == null) {
            throw new IllegalArgumentException(name + " must not be null");
        }
        return value;
    }

}
