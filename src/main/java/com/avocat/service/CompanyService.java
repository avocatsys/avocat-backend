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
    public CompanyDto create(UUID customerId, UUID branchOfficeId, CompanyDto companyDto) {

        var customer = customerService.findById(customerId);

        var branchId = (companyDto.branchOffice() != null) ? companyDto.branchOffice().getId() : branchOfficeId;

        var company = new Company.Builder(
                companyDto.name(), companyDto.cpfCnpj(), companyDto.companyTypes(), branchId, customer)
                .billingEmail(companyDto.billingEmail())
                .description(companyDto.description())
                .stateRegistration(companyDto.stateRegistration())
                .issueDay(companyDto.issueDay())
                .dueDate(companyDto.dueDate())
                .maturityTerm(companyDto.maturityTerm())
                .build();

        return CompanyDto.getInstance(companyRepository.save(company));
    }

    @Transactional
    public CompanyDto update(UUID branchOfficeId, Company company) {
        var companyResult = getCompany(company.getId());
        var saved = companyRepository.save(Company.from(companyResult, company, branchOfficeId));
        return CompanyDto.getInstance(saved);
    }

    @Transactional
    public void delete(UUID companyId) {
    }

    public Page<CompanyDto> findAll(UUID branchOfficeId, Pageable pageable) {
        return companyRepository.findAllByCustomer_Id(branchOfficeId, pageable).map(CompanyDto::getInstance);
    }

    public CompanyDto findById(UUID companyId) {
        var company = getCompany(companyId);
        var branchOffice = branchOfficeService.getBranchOffice(company.getBranchOffice());
        return CompanyDto.getInstanceWithBranchOffice(company, branchOffice);
    }

    public Company getCompany(UUID companyId) {
        return companyRepository.findByIdAndActiveTrue(companyId)
                .orElseThrow(() -> new ResourceNotFoundException("resource not found"));
    }
}
