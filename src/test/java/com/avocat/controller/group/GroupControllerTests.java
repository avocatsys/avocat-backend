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

        mockMvc.perform(get("/v1/branch-office/65344a5e-81ce-4eb3-b16b-955d26b73ede/groups")
                        .header("Authorization", "Bearer " + defaultAccessToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("utf-8"))
                .andExpect(status().isOk())
                .andDo(print());
    }
}
