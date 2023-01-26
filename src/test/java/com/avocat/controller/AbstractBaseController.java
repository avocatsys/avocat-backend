package com.avocat.controller;

import com.avocat.exceptions.ResourceNotFoundException;
import com.avocat.persistence.entity.UserApp;
import com.avocat.persistence.repository.UserAppRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.GrantedAuthority;

import javax.annotation.PostConstruct;
import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Collection;
import java.util.Date;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AbstractBaseController {

    @LocalServerPort
    private int port;

    protected String HOST;

    protected HttpHeaders headers = new HttpHeaders();

    @Autowired
    protected TestRestTemplate testRestTemplate;

    @Value("${token.jwt.secret}")
    private String jwtSecret;

    @Value("${token.jwt.expiration}")
    private long jwtExpiration;

    private SecretKey secretKey;

    protected String defaultAccessToken;

    @Autowired
    private UserAppRepository userRepository;

    @BeforeEach
    public void init(){
        defaultAccessToken = generateToken("efd5cbc3-337b-49d3-8155-3550109c06ca@hotmail.com");
        headers.set("Authorization", "Bearer " + defaultAccessToken);
    }

    @PostConstruct
    public void setUp() {

        HOST = "http://localhost:" + port;

        var secret = Base64.getEncoder().encodeToString(this.jwtSecret.getBytes());
        secretKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
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
