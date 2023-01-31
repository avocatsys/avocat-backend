package com.avocat.service;

import com.avocat.controller.branchoffice.dto.BranchOfficeDto;
import com.avocat.exceptions.ResourceNotFoundException;
import com.avocat.persistence.entity.BranchOffice;
import com.avocat.persistence.repository.BranchOfficeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.UUID;

@Service
public class BranchOfficeService {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private BranchOfficeRepository branchOfficeRepository;

    @Transactional
    public BranchOffice create(UUID customerId, BranchOffice branchOffice) {

        var ownerCustomer = customerService.findById(customerId);
        branchOffice.setCustomer(ownerCustomer);

        return branchOfficeRepository.save(branchOffice);
    }

    @Transactional
    public BranchOffice update(UUID id, BranchOffice branchOffice) {
        var result = getBranchOffice(id);
        return branchOfficeRepository.save(BranchOffice.from(result, branchOffice));
    }

    @Transactional
    public BranchOffice delete(UUID id) {
        var result = getBranchOffice(id);
        return branchOfficeRepository.save(result);
    }

    public Page<BranchOfficeDto> findAllByCustomer_Id(UUID id, Pageable pageable) {
        return branchOfficeRepository.findAllByCustomer_Id(id, pageable).map(BranchOfficeDto::new);
    }

    public BranchOfficeDto findById(UUID branchOfficeId) {
        var result = getBranchOffice(branchOfficeId);//todo fechar a consulta pelo custmer Id.
        return new BranchOfficeDto(result);
    }

    public BranchOffice getBranchOffice(UUID id) {
        return branchOfficeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("resource not found"));
    }
}
