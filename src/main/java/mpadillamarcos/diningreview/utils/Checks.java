package mpadillamarcos.diningreview.utils;

public class Checks {

    public static <T> T require(String name, T value) {
        if (value == null) {
            throw new IllegalArgumentException(name + " must not be null");
        }
        return value;
    }

    public static String requireValidZipcode(String zipcode) {
        if (zipcode == null) {
            throw new IllegalArgumentException("zipcode must not be null");
        }
        if (!zipcode.matches("\\d{5}")) {
            throw new IllegalArgumentException("Invalid US zipcode: " + zipcode);
        }
        return zipcode;
    }

    public static Integer requireValidScore(Integer score) {
        if (score == null) {
            return null;
        }
        if (score < 1 || score > 5) {
            throw new IllegalArgumentException("The score should be an integer between 1 and 5");
        }
        return score;
    }
}
