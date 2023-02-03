package com.avocat.controller.authentication;

import com.avocat.controller.authentication.dto.CredentialsDto;
import com.avocat.exceptions.ResourceNotFoundException;
import com.avocat.persistence.CustomSpecification;
import com.avocat.persistence.entity.Customer;
import com.avocat.persistence.entity.UserApp;
import com.avocat.persistence.repository.CustomerRepository;
import com.avocat.security.custom.CustomAuthenticationManager;
import com.avocat.security.jwt.JwtTokenProvider;
import com.avocat.service.CustomerService;
import com.avocat.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
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

    @Autowired
    private CustomerService customerService;

    @PostMapping("/token")
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
}
