package com.avocat.controller.company;

import com.avocat.common.AbstractMockMvcController;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.UUID;

public class CompanyControllerTests extends AbstractMockMvcController {

    @Test
    void shouldCreateNewCompanyThenWillReturn201() throws Exception {

        String json = getJsonCompany();

        mockMvc.perform(
                        MockMvcRequestBuilders.post("/v1/customer/d5d7da4a-4520-446e-9a6a-aaf4b76f803f/branch-office/65344a5e-81ce-4eb3-b16b-955d26b73ede/companies")
                                .contentType(MediaType.APPLICATION_JSON)
                                .characterEncoding("utf-8")
                                .header("Authorization", "Bearer " + defaultAccessToken)
                                .content(json))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").isNotEmpty());
    }

    private String getJsonCompany() {
        return """
                {                
                    "name":"%s",
                    "cpfCnpj":"%s",
                    "billingEmail":"%s",
                    "description":"%s",
                    "companyTypes":"JURIDICA",
                    "stateRegistration": "",
                    "issueDay": 1,
                    "dueDate": 1,
                    "maturityTerm": 1                                 
                }
                """.formatted(
                UUID.randomUUID().toString().substring(0, 10),
                UUID.randomUUID().toString().substring(0, 20),
                UUID.randomUUID().toString().substring(0, 5) + "@email.com",
                UUID.randomUUID().toString());
    }
}
