package com.avocat.controller.contract.dto;

import com.avocat.controller.company.dto.CompanyDto;
import com.avocat.controller.customer.dto.CustomerDto;
import com.avocat.persistence.entity.Contract;

import java.time.LocalDate;
import java.util.UUID;

public record ContractDto(
        UUID id,
        String name,
        String annotationBilling,
        String generalNote,
        LocalDate adjustmentDate,
        LocalDate closingDate,
        CompanyDto company,
        CustomerDto customer
) {
    private ContractDto(Contract contract) {
        this(
                contract.getId(),
                contract.getName(),
                contract.getAnnotationBilling(),
                contract.getGeneralNote(),
                contract.getAdjustmentDate(),
                contract.getClosingDate(),
                CompanyDto.from(contract.getCompany()),
                CustomerDto.from(contract.getCustomer())
        );
    }

    public static ContractDto from(Contract contract) {
        return new ContractDto(contract);
    }
}
