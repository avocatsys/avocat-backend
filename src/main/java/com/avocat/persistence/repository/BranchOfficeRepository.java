package com.avocat.persistence.repository;

import com.avocat.persistence.entity.BranchOffice;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface BranchOfficeRepository extends JpaRepository<BranchOffice, UUID> {

    Page<BranchOffice> findAllByCustomer_Id(@Param("id") UUID id, Pageable pageable);
}
