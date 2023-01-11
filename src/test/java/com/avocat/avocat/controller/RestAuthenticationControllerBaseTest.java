package com.avocat.avocat.controller;

import com.avocat.controller.authentication.dto.TokenDto;
import com.avocat.persistence.entity.UserApp;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.io.UnsupportedEncodingException;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class RestAuthenticationControllerBaseTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    protected TestRestTemplate restTemplate;

    @LocalServerPort
    protected int port;

    protected String AccessToken;

    protected String host = "http://localhost:";

    @BeforeEach
    void givenUserValid_whenAuthenticate_thenReturnTokenJwtAndHttpStatusOk_20o() throws Exception {

        //@formatter:off
        MvcResult result =this.mockMvc
                .perform(
                        post("/v1/authentication/token")
                                .content(this.objectMapper.writeValueAsBytes(new UserApp.Builder("e77d4056-0284-452b-8156-f20badcc8662@owtest.com", "123").build()))
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.access_token").isNotEmpty())
                .andReturn();
        //@formatter:on

        extractAccessToken(result);
    }

    private void extractAccessToken(MvcResult result) throws JsonProcessingException, UnsupportedEncodingException {
        TokenDto response = objectMapper.readValue(result.getResponse().getContentAsString(), TokenDto.class);
        this.AccessToken = response.getToken();
    }
}
