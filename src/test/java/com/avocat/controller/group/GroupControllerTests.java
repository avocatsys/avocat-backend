package com.avocat.controller.group;

import com.avocat.common.AbstractMockMvcController;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class GroupControllerTests extends AbstractMockMvcController {

    @Test
    void shouldGetAllGroupsByBranchOfficeThenWillReturnPageableOfGroupsAndHttpStatus200() throws Exception {

        mockMvc.perform(get("/v1/branch-office/09ce3d5e-e240-4824-95bf-b4a8e5daa8b1/groups")
                        .header("Authorization", "Bearer " + defaultAccessToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("utf-8"))
                .andExpect(status().isOk())
                .andDo(print());
    }
}
