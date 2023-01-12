package com.avocat.avocat.controller.group;

import com.avocat.persistence.entity.Group;
import com.avocat.persistence.entity.UserApp;
import com.avocat.persistence.repository.UserRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.test.web.servlet.MockMvc;

import javax.annotation.PostConstruct;
import javax.crypto.SecretKey;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.Instant;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class GroupControllerTests {

    @LocalServerPort
    protected int port;

    protected String host = "http://localhost:";

    @Autowired
    protected TestRestTemplate restTemplate;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Value("${token.jwt.secret}")
    private String jwtSecret;

    @Value("${token.jwt.expiration}")
    private long jwtExpiration;

    private SecretKey secretKey;

    @PostConstruct
    public void setUpSecretKey() {
        var secret = Base64.getEncoder().encodeToString(this.jwtSecret.getBytes());
        secretKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }

    @Test
    void givenNewGroup_whenNewGroupCreated_thenHttpStatusCreated_201() throws Exception {

        var newGroup = Group.create("Grupo " + UUID.randomUUID());

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + getToken("e77d4056-0284-452b-8156-f20badcc8662@owtest.com"));

        HttpEntity<Group> request = new HttpEntity<>(newGroup, headers);

        URI uri = new URI(host + port + "/avocat/v1/groups");

        ResponseEntity<Group> result = restTemplate.exchange(uri, HttpMethod.POST, request, Group.class);

        assertEquals(result.getStatusCode(), HttpStatus.CREATED);
    }

    private String getToken(String email) {

        UserApp user = userRepository.findByUsername(email).orElse(null);

        Collection<? extends GrantedAuthority> authorities = user.getAuthorities();

        ArrayList<String> authoritiesList = new ArrayList<>(authorities.size());

        for (GrantedAuthority authority : authorities) {
            authoritiesList.add(authority.getAuthority());
        }

        //@formatter:off
        return Jwts.builder()
                .setSubject(user.getUsername())
                .claim("roles", authoritiesList)
                //.claim("roles", authsList)
                .setIssuedAt(new Date())
                .setExpiration(Date.from(Instant.now().plus(Duration.ofSeconds(jwtExpiration))))
                .signWith(this.secretKey, SignatureAlgorithm.HS256)
                .compact();
        //@formatter:on
    }
}
