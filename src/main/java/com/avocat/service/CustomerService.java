package com.avocat.service;

import com.avocat.controller.customer.dto.CustomerDto;
import com.avocat.exceptions.ResourceNotFoundException;
import com.avocat.persistence.entity.BranchOffice;
import com.avocat.persistence.entity.Customer;
import com.avocat.persistence.entity.UserApp;
import com.avocat.persistence.repository.BranchOfficeRepository;
import com.avocat.persistence.repository.CustomerRepository;
import com.avocat.persistence.repository.PrivilegeRepository;
import com.avocat.persistence.repository.UserAppRepository;
import com.avocat.persistence.types.PrivilegesTypes;
import com.avocat.persistence.types.UserTypes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.UUID;

@Service
public class CustomerService {

    @Autowired
    private UserAppRepository userRepository;

    @Autowired
    private BranchOfficeRepository branchOfficeRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private PrivilegeRepository privilegeRepository;

    @Autowired
    private UserEmailService userEmailService;

    @Transactional
    public CustomerDto signupCustomer(Customer customer) {

        var privilege = privilegeRepository.findByName(PrivilegesTypes.ROLE_OWNER.name());

        var branchOffice = createBranchOfficeDefault(customer);

        var userCreated = userRepository.save(
                new UserApp.Builder(
                        customer.getEmail(), null)
                        .name(customer.getFullName())
                        .privilege(privilege)
                        .branchOffice(branchOffice)
                        .situation(UserTypes.FORGOT_PASSWORD)
                        .oid(UUID.randomUUID())
                        .build());

        customer.setUser(userCreated);
        var result = customerRepository.save(customer);

        userEmailService.sendEmailForgotPassword(userCreated.getUsername());

        return CustomerDto.from(result);
    }

    public Customer findById(UUID customerId) {
        return customerRepository.findById(customerId).orElseThrow(() -> new ResourceNotFoundException("resource not found."));
    }

    private BranchOffice createBranchOfficeDefault(Customer customer) {
        var branchOffice = new BranchOffice.Builder(UUID.randomUUID().toString().substring(0, 20), customer.getEmail(), customer.getOfficeName(), customer.getOfficeName(), customer).build();
        return branchOfficeRepository.save(branchOffice);
    }
}
