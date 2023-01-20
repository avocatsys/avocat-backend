package com.avocat.controller.authentication;

import com.avocat.controller.authentication.dto.TokenDto;
import com.avocat.persistence.entity.UserApp;
import com.avocat.security.custom.CustomAuthenticationManager;
import com.avocat.security.jwt.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RequestMapping(path = "/v1/authentication", produces = MediaType.APPLICATION_JSON_VALUE)
@RestController
public class AuthenticationController {

    @Autowired
    private CustomAuthenticationManager customAuthenticationManager;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @PostMapping("/token")
    public ResponseEntity<TokenDto> authentication(@RequestBody @Valid UserApp userApp) {

            var username = userApp.getUsername();

            var authentication = customAuthenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(username, userApp.getPassword()));

            var token = jwtTokenProvider.genereToken(authentication);

            return ResponseEntity.ok().body(TokenDto.create(token));
    }
}
