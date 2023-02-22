package com.avocat.service;

import com.avocat.controller.company.dto.CompanyDto;
import com.avocat.controller.company.dto.CompanyDto;
import com.avocat.exceptions.ResourceNotFoundException;
import com.avocat.persistence.entity.Privilege;
import com.avocat.persistence.entity.Company;
import com.avocat.persistence.repository.GroupRepository;
import com.avocat.persistence.repository.PrivilegeRepository;
import com.avocat.persistence.repository.CompanyRepository;
import com.avocat.persistence.types.PrivilegesTypes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;

@Service
public class CompanyService {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private BranchOfficeService branchOfficeService;

    @Autowired
    private CompanyRepository companyAppRepository;

    @Autowired
    private PrivilegeRepository privilegeRepository;

    @Autowired
    private GroupRepository groupRepository;

    @Transactional
    public CompanyDto create(UUID customerId, UUID branchOfficeId, Company company) {
        var customer = customerService.findById(customerId);
        company.setBranchOffice(branchOfficeId);
        company.setCustomer(customer);
        return new CompanyDto(companyAppRepository.save(company));
    }

    @Transactional
    public CompanyDto update(UUID branchOfficeId, Company company) {
        return null;
    }

    @Transactional
    public void delete(UUID companyId) {
    }

    public Page<CompanyDto> findAll(UUID branchOfficeId, Pageable pageable) {
        return Page.empty();
    }

    public CompanyDto findById(UUID companyId) {
        return null;
    }
}
