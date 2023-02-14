package com.avocat.controller.user;

import com.avocat.common.AbstractMockMvcController;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.result.StatusResultMatchers;
import org.springframework.util.Assert;

import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class UserAppControllerTests extends AbstractMockMvcController {

    @Test
    void shouldCreateNewUserThenWillReturnHttpStatus201() throws Exception {

        mockMvc.perform(post("/v1/branch-office/65344a5e-81ce-4eb3-b16b-955d26b73ede/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("utf-8")
                        .header("Authorization", "Bearer " + defaultAccessToken)
                        .content(getNewUserAppJson()))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").isNotEmpty());
    }

    @Test
    void shouldGetAllUsersThenWillReturnListOfUsersHttpStatus200() throws Exception {

        mockMvc.perform(get("/v1/customer/d5d7da4a-4520-446e-9a6a-aaf4b76f803f/users")
                        .header("Authorization", "Bearer " + defaultAccessToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("utf-8"))
                        .andDo(print())
                .andExpect(status().isOk());
    }

    private String getNewUserAppJson() {
        return """
                {
                    "name":"%s",
                    "username":"%s",
                    "password":"12345678"
                }
                """.formatted(
                UUID.randomUUID().toString().substring(0, 5),
                UUID.randomUUID().toString().substring(0, 5) + "@owtest.com");
    }
}
