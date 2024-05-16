package mpadillamarcos.diningreview.model;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;

import static mpadillamarcos.diningreview.model.ReviewState.PENDING;
import static mpadillamarcos.diningreview.utils.Checks.require;

@Data
@Entity
@Table(name = "reviews")
@Builder(toBuilder = true)
public class Review {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "restaurant")
    private String restaurant;

    @Column(name = "username")
    private String username;

    @Column(name = "state")
    private ReviewState state;

    @Column(name = "peanut-score")
    private Integer peanutScore;

    @Column(name = "egg-score")
    private Integer eggScore;

    @Column(name = "diary-score")
    private Integer diaryScore;

    @Column(name = "commentary")
    private String commentary;

    public Review(Long id, String restaurant, String username, ReviewState state, Integer peanutScore, Integer eggScore, Integer diaryScore, String commentary) {
        this.id = require("id", id);
        this.restaurant = require("restaurant", restaurant);
        this.username = require("username", username);
        this.state = require("state", state);
        this.peanutScore = peanutScore;
        this.eggScore = eggScore;
        this.diaryScore = diaryScore;
        this.commentary = commentary;
    }

    public static ReviewBuilder newReview() {
        return builder().state(PENDING);
    }
}
