package com.avocat.controller.group;

import com.avocat.controller.BaseTestController;
import com.avocat.exceptions.InvalidPermissionOrRoleException;
import com.avocat.exceptions.ResourceNotFoundException;
import com.avocat.persistence.entity.Group;
import com.avocat.util.JsonUtil;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class GroupControllerTests extends BaseTestController {

    @Test
    void givenNewGroup_whenNewGroupCreated_thenStatusCreated_201() throws Exception {

        mockMvc.perform(
                    post("/v1/groups")
                            .header("Authorization", "Bearer " + defaultAccessToken)
                            .contentType(MediaType.APPLICATION_JSON)
                            .characterEncoding("utf-8")
                            .content(JsonUtil.asJsonString(Group.create("Test" + UUID.randomUUID()))))
                .andExpect(status().isCreated());
    }

    @Test
    void givenAGroup_whenUpdateGroup_thenWillReturnUpdatedGroupAndHttpStatus_200() throws Exception {

        String putJson = """ 
                        { "name": "GROUP_TEST" } 
                        """;

        mockMvc.perform(
                        put("/v1/groups/a56fec46-68a3-4fef-8a2d-c38ecc111516")
                                .header("Authorization", "Bearer " + generateToken("owtestintegration@xemail.com"))
                                .contentType(MediaType.APPLICATION_JSON)
                                .characterEncoding("utf-8")
                                .content(putJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("a56fec46-68a3-4fef-8a2d-c38ecc111516"))
                .andExpect(jsonPath("$.name").value("GROUP_TEST"));
    }

    @Test
    void givenAUUIDGroup_whenDeleteGroup_thenWillReturnHttpStatus_200() throws Exception {

        mockMvc.perform(
                        delete("/v1/groups/c20aa486-0b89-4c87-ae43-5d45ab007375")
                                .header("Authorization", "Bearer " + generateToken("owtestintegration@xemail.com"))
                                .contentType(MediaType.APPLICATION_JSON)
                                .characterEncoding("utf-8"))
                .andExpect(status().isOk());
    }

    @Test
    public void givenNotFound_whenGetSpecificException_thenNotFoundStatus_404() throws Exception {
        String exceptionParam = "not_found";

        mockMvc.perform(get("/v1/groups/6e5775ca-5b5c-4893-95b9-a4e0dce4cb0a", exceptionParam)
                        .header("Authorization", "Bearer " + defaultAccessToken)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof ResourceNotFoundException))
                .andExpect(result -> assertEquals("resource not found", result.getResolvedException().getMessage()));
    }

    @Test
    void givenInvalidPermissionOrRoleException_whenGetSpecificException_thenForbiddenStatus_403() throws Exception {

        mockMvc.perform(
                        post("/v1/groups")
                                .header("Authorization", "Bearer " + generateToken("efd5cbc3-337b-49d3-8155-3550109c06ca@hotmail.com"))
                                .contentType(MediaType.APPLICATION_JSON)
                                .characterEncoding("utf-8")
                                .content(JsonUtil.asJsonString(Group.create("Test" + UUID.randomUUID()))))
                .andExpect(status().isForbidden())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof InvalidPermissionOrRoleException))
                .andExpect(result -> assertEquals("Access is denied. Invalid permission or role", result.getResolvedException().getMessage()));
    }
}
