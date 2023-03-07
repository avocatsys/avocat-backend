package com.avocat.controller.contract;

import com.avocat.controller.contract.dto.ContractDto;
import com.avocat.persistence.entity.Contract;
import com.avocat.service.ContractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_OWNER', 'ROLE_CONTRACT')")
@RequestMapping(path = "/v1/customer/{customerId}", produces = MediaType.APPLICATION_JSON_VALUE)
@RestController
public class ContractController {

    @Autowired
    private ContractService contractService;

    @PostMapping("/branch-office/{branchOfficeId}/company/{companyId}/contracts")
    public ResponseEntity<ContractDto> create(
            @PathVariable(value = "customerId", required = true) UUID customerId,
            @PathVariable(value = "branchOfficeId", required = true) UUID branchOfficeId,
            @PathVariable(value = "companyId", required = true) UUID companyId,
            @RequestBody @Valid Contract contract) {
        return ResponseEntity.status(HttpStatus.CREATED).body(contractService.create(customerId, branchOfficeId, companyId, contract));
    }

    @PutMapping("/company/{companyId}/contracts")
    public ResponseEntity<ContractDto> update(
            @PathVariable("customerId") UUID customerId,
            @PathVariable("companyId") UUID companyId,
            @RequestBody Contract contract) {
        return ResponseEntity.status(HttpStatus.OK).body(contractService.update(customerId, companyId, contract));
    }

    @DeleteMapping("/contracts/{contractId}")
    public ResponseEntity<Void> delete(
            @PathVariable("customerId") UUID customerId,
            @PathVariable("contractId") UUID contractId) {
        contractService.delete(contractId, customerId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/contracts")
    public ResponseEntity<Page<ContractDto>> findAll(
            @PathVariable("customerId") UUID customerId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "100") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.status(HttpStatus.OK).body(contractService.findAll(customerId, pageable));
    }

    @GetMapping("/contracts/{contractId}")
    public ResponseEntity<ContractDto> findById(
            @PathVariable(value = "contractId", required = true) UUID contractId,
            @PathVariable(value = "customerId", required = true) UUID customerId) {
        return ResponseEntity.status(HttpStatus.OK).body(contractService.findById(customerId, contractId));
    }
}
