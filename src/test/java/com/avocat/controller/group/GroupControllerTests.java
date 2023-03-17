package com.avocat.controller.group;

import com.avocat.common.AbstractMockMvcController;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class GroupControllerTests extends AbstractMockMvcController {

    @Test
    void shouldGetAllGroupsByCustomerThenWillReturnPageableOfGroupsAndHttpStatus200() throws Exception {

        mockMvc.perform(get("/v1/customer/d5d7da4a-4520-446e-9a6a-aaf4b76f803f/groups")
                        .header("Authorization", "Bearer " + defaultAccessToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("utf-8"))
                .andExpect(status().isOk())
                .andDo(print());
    }
}
