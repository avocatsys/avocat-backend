package com.avocat.persistence.repository.process;

import com.avocat.persistence.entity.process.LegalBranch;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface LegalBranchRepository extends JpaRepository<LegalBranch, UUID> {

    Page<LegalBranch> findAllByCustomerId(UUID legalBranchId, Pageable pageable);

    Optional<LegalBranch> findByIdAndCustomerId(UUID legalBranchId, UUID customerId);
}
