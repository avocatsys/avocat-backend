package com.avocat.persistence.repository;

import com.avocat.persistence.entity.Contract;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ContractRepository extends JpaRepository<Contract, UUID> {

    Optional<Contract> findByIdAndActiveTrue(UUID contractId);

    Page<Contract> findAllByActiveTrueAndCustomer_Id(UUID branchOfficeId, Pageable pageable);

    Optional<Contract> findByIdAndCustomer_Id(UUID contractId, UUID customerId);

    @Modifying
    @Query("update Contract c set c.active = false where c.id = :contractId and c.customer.id = :customerId")
    void inactiveContract(@Param("contractId") UUID contractId, @Param("customerId") UUID customerId);
}
