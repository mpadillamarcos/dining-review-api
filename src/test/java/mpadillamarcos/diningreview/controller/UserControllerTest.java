package mpadillamarcos.diningreview.controller;

import mpadillamarcos.diningreview.model.User;
import mpadillamarcos.diningreview.service.UserService;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static mpadillamarcos.diningreview.model.Instances.dummyUpdateRequestBuilder;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = UserController.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private UserService userService;

    @Nested
    class CreateNewUser {
        @Test
        void returns_bad_request_when_required_body_is_null() throws Exception {
            mockMvc.perform(post("/users")
                            .content("{}")
                            .contentType(APPLICATION_JSON))
                    .andExpect(status().isBadRequest());
        }

        @Test
        void returns_bad_request_when_required_body_is_not_complete() throws Exception {
            String requestBody = """
                    {
                        "username": "maria123",
                        "city": "San Francisco",
                        "state": "California",
                        "zipcode": 94118,
                        "peanut": true,
                        "dairy": false
                    }
                    """;

            mockMvc.perform(post("/users")
                            .content(requestBody)
                            .contentType(APPLICATION_JSON))
                    .andExpect(status().isBadRequest());
        }

        @Test
        void returns_ok_when_all_required_parameters_are_valid() throws Exception {
            String requestBody = """
                    {
                        "username": "maria123",
                        "city": "San Francisco",
                        "state": "California",
                        "zipcode": 94118,
                        "peanut": false,
                        "egg": false,
                        "dairy": true
                    }
                    """;

            mockMvc.perform(post(
                            "/users")
                            .content(requestBody)
                            .contentType(APPLICATION_JSON))
                    .andExpect(status().isOk());

            verify(userService, times(1))
                    .createNewUser(User.newUser()
                            .username("maria123")
                            .city("San Francisco")
                            .state("California")
                            .zipcode(94118)
                            .peanut(false)
                            .egg(false)
                            .dairy(true)
                            .build()
                    );
        }

    }

    @Nested
    class Update {

        @Test
        void returns_bad_request_when_required_body_is_null() throws Exception {
            mockMvc.perform(put("/users/maria123")
                            .content("{}")
                            .contentType(APPLICATION_JSON))
                    .andExpect(status().isBadRequest());
        }

        @Test
        void returns_bad_request_when_required_body_is_not_complete() throws Exception {
            String requestBody = """
                    {
                        "zipcode": 94118,
                        "peanut": true,
                        "dairy": false
                    }
                    """;

            mockMvc.perform(put("/users/maria123")
                            .content(requestBody)
                            .contentType(APPLICATION_JSON))
                    .andExpect(status().isBadRequest());
        }

        @Test
        void returns_bad_request_when_required_body_is_not_valid() throws Exception {
            String requestBody = """
                    {
                        "city": "San Francisco",
                        "state": "California",
                        "zipcode": 94118,
                        "peanut": "yes",
                        "egg": true
                        "dairy": false
                    }
                    """;

            mockMvc.perform(put("/users/maria123")
                            .content(requestBody)
                            .contentType(APPLICATION_JSON))
                    .andExpect(status().isBadRequest());
        }

        @Test
        void returns_ok_when_all_required_parameters_are_valid() throws Exception {
            String requestBody = """
                    {
                        "city": "San Francisco",
                        "state": "California",
                        "zipcode": 94118,
                        "peanut": true,
                        "egg": true,
                        "dairy": true
                    }
                    """;

            mockMvc.perform(put(
                            "/users/maria123")
                            .content(requestBody)
                            .contentType(APPLICATION_JSON))
                    .andExpect(status().isOk());

            var expectedRequest = dummyUpdateRequestBuilder()
                    .city("San Francisco")
                    .state("California")
                    .zipcode(94118)
                    .peanut(true)
                    .egg(true)
                    .dairy(true)
                    .build();

            verify(userService, times(1))
                    .update("maria123", expectedRequest);
        }
    }
}