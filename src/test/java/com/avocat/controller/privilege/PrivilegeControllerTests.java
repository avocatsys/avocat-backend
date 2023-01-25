package com.avocat.controller.privilege;

import com.avocat.exceptions.ResourceNotFoundException;
import com.avocat.persistence.entity.Privilege;
import com.avocat.persistence.entity.UserApp;
import com.avocat.persistence.repository.UserAppRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.security.core.GrantedAuthority;

import javax.annotation.PostConstruct;
import javax.crypto.SecretKey;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.Instant;
import java.util.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PrivilegeControllerTests {

    @Autowired
    TestRestTemplate testRestTemplate;

    @Value("${token.jwt.secret}")
    private String jwtSecret;

    @Value("${token.jwt.expiration}")
    private long jwtExpiration;

    private SecretKey secretKey;

    @LocalServerPort
    private int port;

    @Autowired
    private UserAppRepository userRepository;

    protected String defaultAccessToken;

    @BeforeEach
    public void init(){
        defaultAccessToken = generateToken("efd5cbc3-337b-49d3-8155-3550109c06ca@hotmail.com");
    }

    @PostConstruct
    public void setUpSecretKey() {
        var secret = Base64.getEncoder().encodeToString(this.jwtSecret.getBytes());
        secretKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }

    @Test
    void givenNewPrivilege_whenPrivilegeSave_thenShouldReturnPrivilegeCreated() throws Exception {

        URI uri = new URI("http://localhost:" + port + "/avocat/v1/branch-office/65344a5e-81ce-4eb3-b16b-955d26b73ede/privileges");

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + defaultAccessToken);

        HttpEntity<List<Privilege>> request = new HttpEntity<>(headers);
        ResponseEntity<List<Privilege>> result = this.testRestTemplate.exchange(uri, HttpMethod.GET, request, new ParameterizedTypeReference<>() {});

        Assertions.assertEquals(result.getStatusCode(), HttpStatus.CREATED);
    }

    public String generateToken(String email) {

        UserApp user = userRepository.findByUsername(email).orElseThrow(() -> new ResourceNotFoundException("user not found" + email));

        Collection<? extends GrantedAuthority> authorities = user.getAuthorities();

        ArrayList<String> authoritiesList = new ArrayList<>(authorities.size());

        for (GrantedAuthority authority : authorities) {
            authoritiesList.add(authority.getAuthority());
        }

        //@formatter:off
        return Jwts.builder()
                .setSubject(user.getUsername())
                .claim("roles", authoritiesList)
                .setIssuedAt(new Date())
                .setExpiration(Date.from(Instant.now().plus(Duration.ofSeconds(jwtExpiration))))
                .signWith(this.secretKey, SignatureAlgorithm.HS256)
                .compact();
        //@formatter:on
    }
}
