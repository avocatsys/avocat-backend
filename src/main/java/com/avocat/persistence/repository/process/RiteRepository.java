package com.avocat.persistence.repository.process;

import com.avocat.persistence.entity.process.Rite;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface RiteRepository extends JpaRepository<Rite, UUID> {

    Page<Rite> findAllByCustomerId(UUID riteId, Pageable pageable);

    Optional<Rite> findByIdAndCustomerId(UUID riteId, UUID customerId);
}
