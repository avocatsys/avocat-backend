package com.avocat.controller.branchoffice.dto;

import com.avocat.controller.customer.dto.CustomerDto;
import com.avocat.persistence.entity.BranchOffice;
import com.avocat.persistence.entity.Customer;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

public record BranchOfficeDto(

        @JsonProperty("branch_office_id")
        UUID id,

        @JsonProperty("corporate_name")
        String corporateName,

        @JsonProperty("branch_office_name")
        String branchOfficeName,

        @JsonProperty("code_office")
        String codeOffice,

        @JsonProperty("state_registration")
        String stateRegistration,

        @JsonProperty("cpf_cnpj")
        String cpfCnpj,

        @JsonProperty("email")
        String email,

        @JsonProperty("customer")
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
