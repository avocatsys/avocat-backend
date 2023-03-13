package com.avocat.persistence.repository.process;

import com.avocat.persistence.entity.process.LegalValue;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface LegalValueRepository extends JpaRepository<LegalValue, UUID> {

    Page<LegalValue> findAllByCustomerId(UUID legalValueId, Pageable pageable);

    Optional<LegalValue> findByIdAndCustomerId(UUID legalValueId, UUID customerId);
}
