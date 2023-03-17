package com.avocat.persistence.repository.process;

import com.avocat.persistence.entity.process.ProceduralPhase;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ProceduralPhaseRepository extends JpaRepository<ProceduralPhase, UUID> {
    
    Page<ProceduralPhase> findAllByCustomerId(UUID proceduralPhaseId, Pageable pageable);

    Optional<ProceduralPhase> findByIdAndCustomerId(UUID proceduralPhaseId, UUID customerId);
}
