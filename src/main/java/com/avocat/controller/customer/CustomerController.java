package com.avocat.controller.customer;

import com.avocat.controller.customer.dto.CustomerDto;
import com.avocat.persistence.entity.Customer;
import com.avocat.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RequestMapping(path = "/v1/customers", produces = MediaType.APPLICATION_JSON_VALUE)
@RestController
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @PostMapping("/signup")
    public ResponseEntity<CustomerDto> signupCustomer(@RequestBody @Valid Customer customer) {
        return ResponseEntity.status(HttpStatus.CREATED).body(customerService.signupCustomer(customer));
    }
}
