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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "restaurant_id", nullable = false)
    private Long restaurantId;

    @Column(name = "username", nullable = false)
    private String username;

    @Column(name = "state")
    private ReviewState state;

    @Column(name = "peanut_score")
    private Integer peanutScore;

    @Column(name = "egg_score")
    private Integer eggScore;

    @Column(name = "dairy_score")
    private Integer dairyScore;

    @Column(name = "commentary")
    private String commentary;

    public Review(Long id, Long restaurantId, String username, ReviewState state, Integer peanutScore, Integer eggScore, Integer dairyScore, String commentary) {
        this.id = id;
        this.restaurantId = require("restaurantId", restaurantId);
        this.username = require("username", username);
        this.state = require("state", state);
        this.peanutScore = peanutScore;
        this.eggScore = eggScore;
        this.dairyScore = dairyScore;
        this.commentary = commentary;
    }

    public Review() {
    }

    public static ReviewBuilder newReview() {
        return builder().state(PENDING);
    }
}
