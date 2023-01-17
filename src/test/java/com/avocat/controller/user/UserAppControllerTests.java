package com.avocat.controller.user;

import com.avocat.controller.BaseTestController;
import com.avocat.persistence.entity.UserApp;
import com.avocat.util.JsonUtil;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class UserAppControllerTests extends BaseTestController {

    @Test
    public void givenNewUserUnPrivileged_whenCreate_thenWillReturnHttpStatusCreated_201() throws Exception {

        var newUser = new UserApp.Builder("newUserTest" + UUID.randomUUID() + "@owtest.com","12345678").build();

        mockMvc.perform(
                        post("/v1/users")
                                .header("Authorization", "Bearer " + defaultAccessToken)
                                .contentType(MediaType.APPLICATION_JSON)
                                .characterEncoding("utf-8")
                                .content(JsonUtil.asJsonString(newUser)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.userId").isNotEmpty());
    }
}
