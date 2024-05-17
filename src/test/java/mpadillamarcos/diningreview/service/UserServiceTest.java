package mpadillamarcos.diningreview.service;

import mpadillamarcos.diningreview.exception.UsernameNotAvailableException;
import mpadillamarcos.diningreview.model.User;
import mpadillamarcos.diningreview.repository.UserRepository;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

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
        void returns_new_user() {
            var newUser = service.createNewUser(dummyUser()
                    .username("maria456")
                    .city("Scranton")
                    .state("Pennsylvania")
                    .zipcode(18509)
                    .peanut(true)
                    .egg(false)
                    .diary(true)
                    .build());

            assertThat(newUser)
                    .returns("maria456", User::getUsername)
                    .returns("Scranton", User::getCity)
                    .returns("Pennsylvania", User::getState)
                    .returns(18509, User::getZipcode)
                    .returns(true, User::getPeanut)
                    .returns(false, User::getEgg)
                    .returns(true, User::getDiary);

        }

        @Test
        void persists_new_user() {
            var newUser = service.createNewUser(dummyUser()
                    .username("maria789")
                    .city("Scranton")
                    .state("Pennsylvania")
                    .zipcode(18509)
                    .peanut(true)
                    .egg(false)
                    .diary(true)
                    .build());

            assertThat(repository.findById(newUser.getUsername()))
                    .get()
                    .returns("maria789", User::getUsername)
                    .returns("Scranton", User::getCity)
                    .returns("Pennsylvania", User::getState)
                    .returns(18509, User::getZipcode)
                    .returns(true, User::getPeanut)
                    .returns(false, User::getEgg)
                    .returns(true, User::getDiary);
        }

        @Test
        void throws_exception_when_username_already_exists() {
            var newUser1 = service.createNewUser(dummyUser()
                    .username("maria")
                    .city("Scranton")
                    .state("Pennsylvania")
                    .zipcode(18509)
                    .peanut(true)
                    .egg(false)
                    .diary(true)
                    .build());
            var newUser2 = dummyUser()
                    .username("maria")
                    .city("San Francisco")
                    .state("California")
                    .zipcode(94118)
                    .peanut(true)
                    .egg(false)
                    .diary(true)
                    .build();

            assertThrows(UsernameNotAvailableException.class, () -> service.createNewUser(newUser2));
        }
    }



}