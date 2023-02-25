package com.avocat.persistence.repository;

import com.avocat.persistence.entity.Contract;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ContractRepository extends JpaRepository<Contract, UUID> {

    Optional<Contract> findByIdAndActiveTrue(UUID contractId);

    Page<Contract> findAllByCustomer_Id(UUID branchOfficeId, Pageable pageable);

    Optional<Contract> findByIdAndCustomer_Id(UUID contractId, UUID customerId);
}
