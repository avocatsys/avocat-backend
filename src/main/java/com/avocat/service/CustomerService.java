package com.avocat.service;

import com.avocat.controller.customer.dto.CustomerDto;
import com.avocat.persistence.entity.Customer;
import com.avocat.persistence.entity.UserApp;
import com.avocat.persistence.repository.CustomerRepository;
import com.avocat.persistence.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class CustomerService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Transactional
    public CustomerDto signupCustomer(Customer customer) {

        var userCreated = userRepository.save(new UserApp.Builder(customer.getEmail(), "1234").build());
        customer.setUser(userCreated);
        var result = customerRepository.save(customer);

        return CustomerDto.from(result);
    }
}
