package com.avocat.persistence.repository.process;

import com.avocat.persistence.entity.process.Paper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface PaperRepository extends JpaRepository<Paper, UUID> {
    
    Page<Paper> findAllByCustomerId(UUID paperId, Pageable pageable);

    Optional<Paper> findByIdAndCustomerId(UUID paperId, UUID customerId);
}
