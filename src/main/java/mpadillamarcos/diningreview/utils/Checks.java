package mpadillamarcos.diningreview.utils;

public class Checks {

    public static <T> T require(String name, T value) {
        if (value == null) {
            throw new IllegalArgumentException(name + " must not be null");
        }
        return value;
    }

    public static Integer requireValidZipcode(Integer zipcode) {
        if (zipcode == null) {
            throw new IllegalArgumentException("zipcode must not be null");
        }
        String zipcodeString = zipcode.toString();
        if (!zipcodeString.matches("\\d{5}") && !zipcodeString.matches("\\d{5}-\\d{4}")) {
            throw new IllegalArgumentException("Invalid US zipcode: " + zipcode);
        }
        return zipcode;

    }
}
