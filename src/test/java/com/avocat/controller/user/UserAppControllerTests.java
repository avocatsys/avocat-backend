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
    public void shouldCreateNewUserThenWillReturnHttpStatus201() throws Exception {

        mockMvc.perform(post("/v1/branch-office/65344a5e-81ce-4eb3-b16b-955d26b73ede/users")
                                .header("Authorization", "Bearer " + defaultAccessToken)
                                .contentType(MediaType.APPLICATION_JSON)
                                .characterEncoding("utf-8")
                                .content(getNewUserAppJson()))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.userId").isNotEmpty());
    }

    @Test
    public void shouldGetAllUsersThenWillReturnListOfUsersHttpStatus200() throws Exception {

        mockMvc.perform(get("/v1/branch-office/65344a5e-81ce-4eb3-b16b-955d26b73ede/users")
                        .header("Authorization", "Bearer " + defaultAccessToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("utf-8").content(getNewUserAppJson()))
                .andExpect(status().isOk())
                .andDo(print());
    }

    private String getNewUserAppJson() {
        return """
                {
                    "username":"%s",
                    "password":"12345678"
                }
                """.formatted(UUID.randomUUID() + "@owtest.com");
    }
}
