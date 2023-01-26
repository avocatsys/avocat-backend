package com.avocat.service;

import com.avocat.exceptions.ResourceNotFoundException;
import com.avocat.persistence.entity.BranchOffice;
import com.avocat.persistence.repository.BranchOfficeRepository;
import org.springframework.beans.factory.annotation.Autowired;
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
        branchOffice.setCustomers(ownerCustomer);

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

    public List<BranchOffice> findAll(BranchOffice branchOffice) {
        return branchOfficeRepository.findAll();//todo fazer o select buscando pelo customerId.
    }

    public BranchOffice findById(UUID id) {
        return getBranchOffice(id);//todo fechar a consulta pelo custmer Id.
    }

    private BranchOffice getBranchOffice(UUID id) {
        return branchOfficeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("resource not found"));
    }
}
