package com.avocat.persistence.repository;

import com.avocat.persistence.entity.BranchOffice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface BranchOfficeRepository extends JpaRepository<BranchOffice, UUID> {
}
