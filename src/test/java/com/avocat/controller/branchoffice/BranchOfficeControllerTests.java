package com.avocat.controller.branchoffice;

import com.avocat.common.AbstractMockMvcController;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.UUID;


public class BranchOfficeControllerTests extends AbstractMockMvcController {

    @Test
    void shouldCreateNewBranchAndReturnHttpStatus201() throws Exception {

        String corporateName = UUID.randomUUID().toString().substring(0, 10);
        String branchOfficeName = UUID.randomUUID().toString().substring(0, 10);
        String codeOffice = UUID.randomUUID().toString().substring(0, 5);
        String stateRegistration = UUID.randomUUID().toString().substring(0, 5);
        String email = UUID.randomUUID().toString().substring(0, 5) + "@testintegration.com";

        String json = getJsonBrachOffice(corporateName, branchOfficeName, codeOffice, stateRegistration, email);

        mockMvc.perform(
                        MockMvcRequestBuilders.post("/v1/customer/d5d7da4a-4520-446e-9a6a-aaf4b76f803f/branch-offices")
                                .contentType(MediaType.APPLICATION_JSON)
                                .characterEncoding("utf-8")
                                .header("Authorization", "Bearer " + defaultAccessToken)
                                .content(json))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.branchOfficeName").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.cpfCnpj").isNotEmpty());
    }

    @Test
    void shouldGetAllBranchOfficeByCustomerIdAndWillReturn200() throws Exception {

        mockMvc.perform(
                MockMvcRequestBuilders.get("/v1/customer/d5d7da4a-4520-446e-9a6a-aaf4b76f803f/branch-offices")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("utf-8")
                        .header("Authorization", "Bearer " + defaultAccessToken))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    void shouldGetBranchOfficeByCustomerIdAndWillReturn200() throws Exception {

        mockMvc.perform(
                        MockMvcRequestBuilders.get("/v1/customer/d5d7da4a-4520-446e-9a6a-aaf4b76f803f/branch-offices/65344a5e-81ce-4eb3-b16b-955d26b73ede")
                                .contentType(MediaType.APPLICATION_JSON)
                                .characterEncoding("utf-8")
                                .header("Authorization", "Bearer " + defaultAccessToken))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

    private String getJsonBrachOffice(String corporateName, String branchOfficeName, String codeOffice, String stateRegistration, String email) {
        return """
                {
                    "corporateName": "%s",
                    "branchOfficeName": "%s",
                    "codeOffice": "%s",
                    "stateRegistration": "%s",
                    "cpfCnpj": "15660103000155",
                    "email": "%s"                                        
                }
                """.formatted(corporateName, branchOfficeName, codeOffice, stateRegistration, email);
    }
}
