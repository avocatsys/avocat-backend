package com.avocat.service;

import com.avocat.controller.customer.dto.CustomerDto;
import com.avocat.exceptions.ResourceNotFoundException;
import com.avocat.persistence.CustomSpecification;
import com.avocat.persistence.entity.Customer;
import com.avocat.persistence.entity.UserApp;
import com.avocat.persistence.repository.CustomerRepository;
import com.avocat.persistence.repository.PrivilegeRepository;
import com.avocat.persistence.repository.UserAppRepository;
import com.avocat.persistence.types.PrivilegesTypes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.UUID;

@Service
public class CustomerService {

    @Autowired
    private UserAppRepository userRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private PrivilegeRepository privilegeRepository;

    @Transactional
    public CustomerDto signupCustomer(Customer customer) {

        var privilege = privilegeRepository.findByName(PrivilegesTypes.ROLE_ADMIN.name());

        var userCreated = userRepository.save(
                new UserApp.Builder(customer.getEmail(), "12345678").name(customer.getFullName()).privilege(privilege).build());

        customer.setUser(userCreated);
        var result = customerRepository.save(customer);

        return CustomerDto.from(result);
    }

    public Customer findById(UUID customerId) {
        return customerRepository.findById(customerId).orElseThrow(() -> new ResourceNotFoundException("resource not found."));
    }

    public Customer findCustomerJoinUserAppByUserName(String username) throws Throwable {
        var userResult = customerRepository
                .findOne(Specification.where(CustomSpecification.byUsername(username)));

        return (Customer) userResult.orElseThrow(() -> new ResourceNotFoundException("resource not found"));
    }
}
