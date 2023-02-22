package com.avocat.controller.company.dto;

import com.avocat.persistence.entity.BranchOffice;
import com.avocat.persistence.entity.Company;
import com.avocat.persistence.entity.Customer;
import com.avocat.persistence.types.CompanyTypes;

import java.util.UUID;

public record CompanyDto(
        UUID id,
        String name,
        String cpfCnpj,
        String billingEmail,
        String description,
        CompanyTypes companyTypes,
        String stateRegistration,
        Integer issueDay,
        Integer dueDate,
        Integer maturityTerm,
        Customer customer
) {
    public CompanyDto(Company company) {
        this(
                company.getId(),
                company.getName(),
                company.getCpfCnpj(),
                company.getBillingEmail(),
                company.getDescription(),
                company.getCompanyTypes(),
                company.getStateRegistration(),
                company.getIssueDay(),
                company.getDueDate(),
                company.getMaturityTerm(),
                company.getCustomer()
        );
    }
}
