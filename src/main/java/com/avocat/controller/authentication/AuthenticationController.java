package com.avocat.controller.authentication;

import com.avocat.controller.authentication.dto.CredentialsDto;
import com.avocat.controller.authentication.dto.LoginDto;
import com.avocat.exceptions.ResourceNotFoundException;
import com.avocat.persistence.repository.UserAppRepository;
import com.avocat.security.custom.CustomAuthenticationManager;
import com.avocat.security.jwt.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RequestMapping(path = "/v1/authentication/token", produces = MediaType.APPLICATION_JSON_VALUE)
@RestController
public class AuthenticationController {

    @Autowired
    private CustomAuthenticationManager customAuthenticationManager;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private UserAppRepository userAppRepository;

    @PostMapping
    public ResponseEntity<CredentialsDto> authentication(@RequestBody LoginDto login) throws Throwable {

        var username = login.getUsername();

        var userAuthenticate = userAppRepository.findByUsernameAuthenticate(login.getUsername())
                .orElseThrow(() -> new ResourceNotFoundException("user not found: " + username));

        var authentication = customAuthenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(username, login.getPassword()));

        var token = jwtTokenProvider.genereToken(authentication);

        UUID branchOfficeId = null;

        if(userAuthenticate.getBranchOffice() != null)
            branchOfficeId = userAuthenticate.getBranchOffice().getId();

        return ResponseEntity.ok().body(CredentialsDto
                .create(token, userAuthenticate.getBranchOffice().getCustomer().getId(), branchOfficeId, userAuthenticate.getUsername(), userAuthenticate.getName()));
    }

    @GetMapping("/{token}")
    public ResponseEntity<Void> validateToken(@PathVariable("token") String token) {
        return jwtTokenProvider.validateToken(token)? ResponseEntity.ok().build() : ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
}
