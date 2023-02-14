package com.avocat.controller.authentication;

import com.avocat.controller.authentication.dto.CredentialsDto;
import com.avocat.controller.authentication.dto.LoginDto;
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
import java.util.UUID;

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
    public ResponseEntity<CredentialsDto> authentication(@RequestBody LoginDto login) throws Throwable {

        var username = login.getUsername();

        Customer userLogged = customerService.findCustomerJoinUserAppByUserName(username);

        var authentication = customAuthenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(username, login.getPassword()));

        var token = jwtTokenProvider.genereToken(authentication);

        UUID branchOfficeId = null;

        if(userLogged.getUser().getBranchOffice() != null)
            branchOfficeId = userLogged.getUser().getBranchOffice().getId();

        return ResponseEntity.ok().body(CredentialsDto
                .create(token, userLogged.getId(), branchOfficeId, userLogged.getUser().getUsername(), userLogged.getUser().getName()));
    }

    @GetMapping("/{token}")
    public ResponseEntity<Void> validateToken(@PathVariable("token") String token) {
        return jwtTokenProvider.validateToken(token)? ResponseEntity.ok().build() : ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
}
