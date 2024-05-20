package mpadillamarcos.diningreview.service;

import mpadillamarcos.diningreview.exception.NotFoundException;
import mpadillamarcos.diningreview.exception.UsernameNotAvailableException;
import mpadillamarcos.diningreview.model.User;
import mpadillamarcos.diningreview.repository.UserRepository;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static mpadillamarcos.diningreview.model.Instances.dummyUpdateRequestBuilder;
import static mpadillamarcos.diningreview.model.Instances.dummyUser;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class UserServiceTest {

    @Autowired
    private UserRepository repository;

    @Autowired
    private UserService service;

    @Nested
    class CreateNewUser {

        @Test
        void persists_new_user() {
            var newUser = dummyUser()
                    .username("maria789")
                    .city("Scranton")
                    .state("Pennsylvania")
                    .zipcode(18509)
                    .peanut(true)
                    .egg(false)
                    .dairy(true)
                    .build();

            service.createNewUser(newUser);

            assertThat(repository.findById(newUser.getUsername()))
                    .get()
                    .returns("maria789", User::getUsername)
                    .returns("Scranton", User::getCity)
                    .returns("Pennsylvania", User::getState)
                    .returns(18509, User::getZipcode)
                    .returns(true, User::getPeanut)
                    .returns(false, User::getEgg)
                    .returns(true, User::getDairy);
        }

        @Test
        void throws_exception_when_username_already_exists() {
            var newUser1 = dummyUser()
                    .username("maria")
                    .city("Scranton")
                    .state("Pennsylvania")
                    .zipcode(18509)
                    .peanut(true)
                    .egg(false)
                    .dairy(true)
                    .build();

            service.createNewUser(newUser1);

            var newUser2 = dummyUser()
                    .username("maria")
                    .city("San Francisco")
                    .state("California")
                    .zipcode(94118)
                    .peanut(true)
                    .egg(false)
                    .dairy(true)
                    .build();

            assertThrows(UsernameNotAvailableException.class, () -> service.createNewUser(newUser2));
        }
    }

    @Nested
    class Update {
        @Test
        void throws_not_found_exception_when_username_does_not_exist() {
            var username = "maria007";
            var request = dummyUpdateRequestBuilder().peanut(false).build();

            assertThrows(NotFoundException.class, () -> service.update(username, request));
        }

        @Test
        void persists_updated_user() {
            var user = dummyUser().username("maria009").build();
            service.createNewUser(user);

            var updateRequest = dummyUpdateRequestBuilder()
                    .city("San Francisco")
                    .state("California")
                    .zipcode(94118)
                    .peanut(true)
                    .egg(true)
                    .dairy(true)
                    .build();
            service.update(user.getUsername(), updateRequest);

            assertThat(repository.findById(user.getUsername()))
                    .get()
                    .returns("maria009", User::getUsername)
                    .returns("San Francisco", User::getCity)
                    .returns("California", User::getState)
                    .returns(94118, User::getZipcode)
                    .returns(true, User::getPeanut)
                    .returns(true, User::getEgg)
                    .returns(true, User::getDairy);
        }
    }

}