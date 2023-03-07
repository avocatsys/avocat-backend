package com.avocat.service;

import com.avocat.controller.contract.dto.ContractDto;
import com.avocat.exceptions.ResourceNotFoundException;
import com.avocat.persistence.entity.Contract;
import com.avocat.persistence.repository.ContractRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.UUID;

@Service
public class ContractService {


    @Autowired
    private ContractRepository contractRepository;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private CompanyService companyService;

    @Transactional
    public ContractDto create(UUID customerId, UUID branchOfficeId, UUID companyId, Contract contract) {

        var customer = customerService.findById(customerId);
        var company = companyService.getCompany(companyId);

        contract.setBranchOffice(branchOfficeId);
        contract.setCustomer(customer);
        contract.setCompany(company);

        return ContractDto.from(contractRepository.save(contract));
    }

    @Transactional
    public ContractDto update(UUID branchOfficeId, UUID companyId, Contract contract) {

        var company = companyService.getCompany(companyId);
        var contractResult = getContract(contract.getId());
        var saved = contractRepository.save(Contract.convertValue(contractResult, contract, company, branchOfficeId));

        return ContractDto.from(saved);
    }

    @Transactional
    public void delete(UUID contractId, UUID customerId) {
        contractRepository.inactiveContract(contractId, customerId);
    }

    public Page<ContractDto> findAll(UUID branchOfficeId, Pageable pageable) {
        return contractRepository.findAllByActiveTrueAndCustomer_Id(branchOfficeId, pageable).map(ContractDto::from);
    }

    public ContractDto findById(UUID customerId, UUID contractId) {
        var result = contractRepository.findByIdAndCustomer_Id(contractId, customerId)
                .orElseThrow(() -> new ResourceNotFoundException("resource not found"));
        return ContractDto.from(result);
    }

    private Contract getContract(UUID contractId) {
        return contractRepository.findByIdAndActiveTrue(contractId)
                .orElseThrow(() -> new ResourceNotFoundException("resource not found"));
    }
}
