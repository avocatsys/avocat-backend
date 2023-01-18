package com.avocat.controller.user;

import com.avocat.controller.BaseTestController;
import com.avocat.persistence.entity.BranchOffice;
import com.avocat.persistence.entity.Privilege;
import com.avocat.persistence.entity.UserApp;
import com.avocat.util.JsonUtil;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class UserAppControllerTests extends BaseTestController {

    @Test
    public void givenNewUserWithPrivilegedAdmin_whenCreate_thenWillReturnHttpStatusCreated_201() throws Exception {

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

    @Test
    public void givenUser_whenUpdate_thenWillReturnUserUpdatedAndHttpStatusOk_200() throws Exception {

        var user = new UserApp.Builder("owtest@gmail.com", null).build();

        mockMvc.perform(
                        put("/v1/users/c17ceaa8-5475-450b-9409-f3545548ca6d/branch-offices/65344a5e-81ce-4eb3-b16b-955d26b73ede")
                                .header("Authorization", "Bearer " + defaultAccessToken)
                                .contentType(MediaType.APPLICATION_JSON)
                                .characterEncoding("utf-8")
                                .content(JsonUtil.asJsonString(user)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.userId").isNotEmpty());
    }
}
