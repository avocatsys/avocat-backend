package com.avocat.controller.authentication;

import com.avocat.persistence.entity.UserApp;
import com.avocat.persistence.repository.UserRepository;
import com.avocat.util.JsonUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
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

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class AuthenticationControllerTest {

    @MockBean
    private UserRepository userRepository;

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        //@formatter:off
        given(
            this.userRepository.findByUsername("owtest@malito.com"))
            .willReturn(Optional.of(
                    new UserApp.Builder("owtest@malito.com", "$2a$10$ielFeDLFnuavoyASyyfA4.W6L8N2vMLFa5JMF5aPpMw5InBY1.fnK").build()
            ));
        //@formatter:on
    }

    @Test
    public void givenUserToAuthentication_whenAuthenticate_thenWillGenerateToken_200() throws Exception {
        //@formatter:off
        this.mockMvc
            .perform(
                post("/v1/authentication/token")
                .content(JsonUtil.asJsonString(new UserApp.Builder("owtest@malito.com", "123").build()))
                .contentType(MediaType.APPLICATION_JSON)
            )
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.access_token").isNotEmpty());
        //@formatter:on
    }
}
