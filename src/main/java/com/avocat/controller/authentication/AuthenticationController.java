package com.avocat.controller.authentication;

import com.avocat.controller.authentication.dto.CredentialsDto;
import com.avocat.persistence.entity.Customer;
import com.avocat.persistence.entity.UserApp;
import com.avocat.security.custom.CustomAuthenticationManager;
import com.avocat.security.jwt.JwtTokenProvider;
import com.avocat.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequestMapping(path = "/v1/authentication/token", produces = MediaType.APPLICATION_JSON_VALUE)
@RestController
public class AuthenticationController {

    @Autowired
    private CustomAuthenticationManager customAuthenticationManager;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private CustomerService customerService;

    @PostMapping
    public ResponseEntity<CredentialsDto> authentication(@RequestBody @Valid UserApp userApp) throws Throwable {

        var username = userApp.getUsername();

        Customer userLogged = customerService.findCustomerJoinUserAppByUserName(username);

        var authentication = customAuthenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(username, userApp.getPassword()));

        var token = jwtTokenProvider.genereToken(authentication);
        //todo depois fazer adaptar a query para retornar o customerId
        return ResponseEntity.ok().body(CredentialsDto
                .create(token, userLogged.getId(), null, userLogged.getUser().getUsername()));
    }

    @GetMapping("/{token}")
    public ResponseEntity<Void> validateToken(@PathVariable("token") String token) {
        return jwtTokenProvider.validateToken(token)? ResponseEntity.ok().build() : ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
}
