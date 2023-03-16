package com.avocat.service;

import com.avocat.controller.process.dto.ProcessDto;
import com.avocat.persistence.entity.process.Process;
import com.avocat.persistence.repository.process.ProcessRepository;
import com.avocat.persistence.types.MessageSystem;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.transaction.Transactional;
import java.util.UUID;

@Service
public class ProcessService {

    private final ProcessRepository processRepository;

    private final CustomerService customerService;

    private final ContractService contractService;

    private final CompanyService companyService;

    public ProcessService(
            ProcessRepository processRepository,
            CustomerService customerService,
            ContractService contractService,
            CompanyService companyService) {
        this.processRepository = processRepository;
        this.customerService = customerService;
        this.contractService = contractService;
        this.companyService = companyService;
    }

    @Transactional
    public Process create(ProcessDto processDto, UUID customerId) {

        var customer = customerService.findById(customerId);
        var contract = contractService.getContract(processDto.contractId());

        var principal = companyService.getCompany(processDto.principalId());
        var contrary = companyService.getCompany(processDto.contraryId());

        var builder = new Process.Builder(
                processDto.processNumber(),
                principal,
                contrary,
                principal.getName(),
                contrary.getName(),
                processDto.area(),
                contract,
                customer)
                .auxiliaryCode(processDto.auxiliaryCode())
                .internalObservation(processDto.internalObservation())
                .closingObservation(processDto.closingObservation())
                .customerObservation(processDto.customerObservation())
                .financialObservation(processDto.financialObservation())
                .detailObject(processDto.detailObject())
                .dateEntry(processDto.dateEntry())
                .distributionDate(processDto.distributionDate())
                .action(processDto.action())
                .judicialDistrict(processDto.judicialDistrict())
                .forum(processDto.forum())
                .rite(processDto.rite())
                .build();

        return processRepository.save(builder);
    }
}
