package com.avocat.controller.group;

import com.avocat.controller.BaseTestController;
import com.avocat.persistence.entity.Group;
import com.avocat.util.JsonUtil;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class GroupControllerTests extends BaseTestController {

    @Test
    void givenNewGroup_whenNewGroupCreated_thenHttpStatusCreated_201() throws Exception {
        
        String accessToken = generateToken("e77d4056-0284-452b-8156-f20badcc8662@owtest.com");

        mockMvc.perform(
                    post("/v1/groups")
                            .header("Authorization", "Bearer " + accessToken)
                            .contentType(MediaType.APPLICATION_JSON)
                            .characterEncoding("utf-8")
                            .content(JsonUtil.asJsonString(Group.create("Test" + UUID.randomUUID()))))
                .andExpect(status().isCreated());

    }
}
