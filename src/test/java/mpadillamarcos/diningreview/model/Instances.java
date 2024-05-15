package mpadillamarcos.diningreview.model;

import mpadillamarcos.diningreview.model.User.UserBuilder;

import static mpadillamarcos.diningreview.model.User.newUser;

public class Instances {

    public static UserBuilder dummyUser() {
        return newUser()
                .username("maria123")
                .city("San Francisco")
                .state("California")
                .zipcode(94118)
                .peanut(false)
                .egg(false)
                .diary(true);

    }
}
