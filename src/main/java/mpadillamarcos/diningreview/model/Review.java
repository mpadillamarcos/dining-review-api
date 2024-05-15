package mpadillamarcos.diningreview.model;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;

import static mpadillamarcos.diningreview.utils.Checks.require;

@Data
@Entity
@Table(name = "reviews")
@Builder(toBuilder = true)
public class Review {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "username")
    private String username;

    @Column(name = "peanut-score")
    private Integer peanutScore;

    @Column(name = "egg-score")
    private Integer eggScore;

    @Column(name = "diary-score")
    private Integer diaryScore;

    @Column(name = "commentary")
    private String commentary;

    public Review(Long id, String username, Integer peanutScore, Integer eggScore, Integer diaryScore, String commentary) {
        this.id = require("id", id);
        this.username = require("username", username);
        this.peanutScore = peanutScore;
        this.eggScore = eggScore;
        this.diaryScore = diaryScore;
        this.commentary = commentary;
    }

    public static ReviewBuilder newReview() {
        return builder();
    }
}
