package com.avocat.persistence.repository.process;

import com.avocat.persistence.entity.process.Process;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ProcessRepository extends JpaRepository<Process, UUID> {

    Page<Process> findAllByCustomerId(UUID customerId, Pageable pageable);
}
