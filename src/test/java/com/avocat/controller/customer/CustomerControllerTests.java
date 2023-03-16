package com.avocat.controller.customer;

import com.avocat.common.AbstractMockMvcController;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class CustomerControllerTests extends AbstractMockMvcController {

    @Test
    public void shouldCreateNewCustomerThenWillReturnHttpStatus201() throws Exception {

        this.mockMvc
                .perform(
                        post("/v1/customers/signup")
                                .content(getNewCustomerJson())
                                .contentType(MediaType.APPLICATION_JSON)
                ).andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").isNotEmpty());
    }

    private String getNewCustomerJson() {
        return """
                {                    
                    "fullName":"%s",
                    "officeName":"%s",
                    "email":"%s"               
                }
                """.formatted(
                "Test integration " + UUID.randomUUID().toString().substring(0, 10),
                "Test integration " + UUID.randomUUID().toString().substring(0, 10),
                UUID.randomUUID().toString().substring(0, 10) + "@test.com");
    }
}
