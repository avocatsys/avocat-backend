package com.avocat.service;

import com.avocat.controller.company.dto.CompanyDto;
import com.avocat.exceptions.ResourceNotFoundException;
import com.avocat.persistence.entity.Company;
import com.avocat.persistence.repository.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
    private CompanyRepository companyRepository;

    @Transactional
    public CompanyDto create(UUID customerId, UUID branchOfficeId, Company company) {
        var customer = customerService.findById(customerId);
        company.setBranchOffice(branchOfficeId);
        company.setCustomer(customer);
        return new CompanyDto(companyRepository.save(company));
    }

    @Transactional
    public CompanyDto update(UUID branchOfficeId, Company company) {
        var companyResult = getCompany(company.getId());
        var saved = companyRepository.save(Company.from(companyResult, company, branchOfficeId));
        return new CompanyDto(saved);
    }

    @Transactional
    public void delete(UUID companyId) {
    }

    public Page<CompanyDto> findAll(UUID branchOfficeId, Pageable pageable) {
        return companyRepository.findAllByCustomer_Id(branchOfficeId, pageable).map(CompanyDto::new);
    }

    public CompanyDto findById(UUID companyId) {
        return new CompanyDto(this.getCompany(companyId));
    }

    private Company getCompany(UUID companyId) {
        return companyRepository.findByIdAndActiveTrue(companyId)
                .orElseThrow(() -> new ResourceNotFoundException("resource not found"));
    }
}
