package com.avocat.controller.customer;

import com.avocat.persistence.entity.Customer;
import com.avocat.util.JsonUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class CustomerControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void givenCustomerNew_whenSignup_thenSignupCustomer_201() throws Exception {

        var fullName = UUID.randomUUID();
        var officeNAme = "Test Integration " + fullName;
        var email = fullName + "@testintegration.com";

        //@formatter:off
        this.mockMvc
                .perform(
                        post("/v1/customers/signup")
                                .content(JsonUtil.asJsonString(Customer.getInstanceWithOutUser(fullName.toString(), officeNAme, email)))
                                .contentType(MediaType.APPLICATION_JSON)
                ).andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.customerId").isNotEmpty());
        //@formatter:on
    }
}
