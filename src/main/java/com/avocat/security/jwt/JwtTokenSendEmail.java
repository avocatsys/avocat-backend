package com.avocat.security.jwt;

import com.avocat.exceptions.ResourceNotFoundException;
import com.avocat.persistence.repository.UserAppRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.Instant;
import java.util.Base64;
import java.util.Date;
import java.util.Objects;

@Component
public class JwtTokenSendEmail {

    @Value("${server.address.host}")
    private String serverAddressHost;

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

    @Autowired
    private UserAppRepository userAppRepository;

    public String generateTokenToSendEmail(String email) {
        var user = userAppRepository.findByUsername(email)
                .orElseThrow(() -> new ResourceNotFoundException("resource not found"));

        Objects.requireNonNull(user.getOid(), "oid can`t be null");
        //@formatter:off
        var token = Jwts.builder()
                .setSubject(user.getOid().toString())
                .setIssuedAt(new Date())
                .setExpiration(Date.from(Instant.now().plus(Duration.ofSeconds(jwtExpiration))))
                .signWith(this.secretKey, SignatureAlgorithm.HS256)
                .compact();
        //@formatter:on

        var link = String.format("%s/#/login?key=%s", serverAddressHost, token);
        userAppRepository.updateLinkForgot(link, user.getId());
        return link;
    }
}
