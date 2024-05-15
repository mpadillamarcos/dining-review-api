package mpadillamarcos.diningreview.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Data;

import static mpadillamarcos.diningreview.utils.Checks.require;

@Data
@Entity
@Table(name = "users")
@Builder(toBuilder = true)
public class User {

    @Id
    private String username;

    @Column(name = "city")
    private String city;

    @Column(name = "state")
    private String state;

    @Column(name = "zipcode")
    private Integer zipcode;

    @Column(name = "peanut")
    private Boolean peanut;

    @Column(name = "egg")
    private Boolean egg;

    @Column(name = "diary")
    private Boolean diary;

    public User(String username, String city, String state, Integer zipcode, Boolean peanut, Boolean egg, Boolean diary) {
        this.username = require("username", username);
        this.city = require("city", city);
        this.state = require("state", state);
        this.zipcode = require("zipcode", zipcode);
        this.peanut = require("peanut", peanut);
        this.egg = require("egg", egg);
        this.diary = require("diary", diary);
    }

    public static UserBuilder newUser() {
        return builder();
    }
}
