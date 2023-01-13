package com.avocat.security.jwt;

import com.avocat.persistence.entity.UserApp;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Component
public class JwtTokenProvider {

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

    public String genereToken(Authentication auth) {

        UserApp userApp = (UserApp) auth.getPrincipal();

        Collection<? extends GrantedAuthority> authorities = auth.getAuthorities();

        ArrayList<String> authoritiesList = new ArrayList<>(authorities.size());

        for (GrantedAuthority authority : authorities) {
            authoritiesList.add(authority.getAuthority());
        }

        //@formatter:off
        return Jwts.builder()
                .setSubject(userApp.getUsername())
                .claim("roles", authoritiesList)
                .setIssuedAt(new Date())
                .setExpiration(Date.from(Instant.now().plus(Duration.ofSeconds(jwtExpiration))))
                .signWith(this.secretKey, SignatureAlgorithm.HS256)
                .compact();
        //@formatter:on
    }

    public Authentication getAuthentication(String token) {

        Claims body = Jwts.parser()
                .setSigningKey(this.secretKey)
                .parseClaimsJws(token)
                .getBody();

        String username = body.getSubject();
        List<GrantedAuthority> authorities = getRoles(body);

        return new UsernamePasswordAuthenticationToken(username, token, authorities);
    }

    private List<GrantedAuthority> getRoles(Claims body) {
        Collection<?> roles = body.get("roles", Collection.class);
        if(roles != null) {
            return roles.stream()
                    .map(authority -> new SimpleGrantedAuthority(authority.toString()))
                    .collect(Collectors.toList());
        } else {
            return Collections.emptyList();
        }
    }

    public boolean validateToken(String token) {
        try {
            Jws<Claims> claims = Jwts.parserBuilder().setSigningKey(this.secretKey).build().parseClaimsJws(token);
            log.info("expiration date: {}", claims.getBody().getExpiration());
            return true;
        } catch (RuntimeException ex) {
            log.error("Invalid JWT token: {}", ex.getMessage());
        }
        return false;
    }
}
