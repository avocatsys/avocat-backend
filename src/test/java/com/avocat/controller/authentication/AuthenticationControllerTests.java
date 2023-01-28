package com.avocat.controller.authentication;

import com.avocat.common.AbstractMockMvcController;
import com.avocat.persistence.entity.UserApp;
import com.avocat.persistence.repository.UserAppRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class AuthenticationControllerTests extends AbstractMockMvcController {

    @Test
    public void shouldMakeAuthenticationOfUserThenWillReturnGeneratedTokenWithHttpStatus200() throws Exception {

        mockMvc.perform(
                        post("/v1/authentication/token")
                                .content(getUserAppJson())
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.access_token").isNotEmpty());
    }

    private String getUserAppJson() {
        return """
                {
                    "username":"e77d4056-0284-452b-8156-f20badcc8662@owtest.com",
                    "password":"123"
                }
                """;
    }
}
