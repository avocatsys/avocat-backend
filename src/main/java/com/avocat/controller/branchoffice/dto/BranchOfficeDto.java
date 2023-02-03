package com.avocat.controller.branchoffice.dto;

import com.avocat.controller.customer.dto.CustomerDto;
import com.avocat.persistence.entity.BranchOffice;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

public record BranchOfficeDto(
        UUID id,
        String corporateName,
        String branchOfficeName,
        String codeOffice,
        String stateRegistration,
        String cpfCnpj,
        String email,
        CustomerDto customerDto
) {
    public BranchOfficeDto(BranchOffice branchOffice) {
        this(
                branchOffice.getId(),
                branchOffice.getCorporateName(),
                branchOffice.getBranchOfficeName(),
                branchOffice.getCodeOffice(),
                branchOffice.getStateRegistration(),
                branchOffice.getCpfCnpj(),
                branchOffice.getEmail(),
                CustomerDto.from(branchOffice.getCustomer())
        );
    }
}
