package mpadillamarcos.diningreview.controller;

import mpadillamarcos.diningreview.model.User;
import mpadillamarcos.diningreview.model.UserRequest;
import mpadillamarcos.diningreview.service.UserService;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static mpadillamarcos.diningreview.model.Instances.dummyUpdateRequestBuilder;
import static mpadillamarcos.diningreview.model.Instances.dummyUser;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

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
        void returns_bad_request_when_zipcode_is_not_valid() throws Exception {
            String requestBody = """
                    {
                        "username": "maria123",
                        "city": "San Francisco",
                        "state": "California",
                        "zipcode": 123456789,
                        "peanut": true,
                        "egg": false,
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
                    .createNewUser(UserRequest.builder()
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

    @Nested
    class FindUser {
        @Test
        void returns_user_information_when_exists() throws Exception {
            when(userService.find("maria123"))
                    .thenReturn(Optional.of(dummyUser().build()));

            mockMvc.perform(get("/users/maria123"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.username", equalTo("maria123")))
                    .andExpect(jsonPath("$.city", equalTo("San Francisco")))
                    .andExpect(jsonPath("$.state", equalTo("California")))
                    .andExpect(jsonPath("$.zipcode", equalTo(94118)))
                    .andExpect(jsonPath("$.peanut", equalTo(false)))
                    .andExpect(jsonPath("$.egg", equalTo(false)))
                    .andExpect(jsonPath("$.dairy", equalTo(true)));
        }

        @Test
        void returns_not_found_exception_when_username_does_not_exist() throws Exception {
            when(userService.find("maria123"))
                    .thenReturn(Optional.empty());

            mockMvc.perform(get("/users/maria123"))
                    .andExpect(status().isNotFound())
                    .andExpect(jsonPath("$.message", equalTo("Username not found")));
        }
    }
}