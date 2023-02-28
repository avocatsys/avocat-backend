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
        Integer issueDay,//dia de emissão da nota para todos os contratos.
        Integer dueDate,//dia de vencimento de todos os contratos.
        Integer maturityTerm,//prazo para vencimento após a emissão da nota 5 dias, 10 dias, 15 dias.
        Customer customer,
        BranchOffice branchOffice
) {
    private CompanyDto(Company company) {
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
                company.getCustomer(),
                null
        );
    }

    public static CompanyDto from(Company company) {
        return new CompanyDto(company);
    }
}
