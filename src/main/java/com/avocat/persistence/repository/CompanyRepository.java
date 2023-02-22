package com.avocat.persistence.repository;

import com.avocat.persistence.entity.Company;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface CompanyRepository extends JpaRepository<Company, UUID> {

    Optional<Company> findByIdAndActiveTrue(UUID companyId);

    Page<Company> findAllByCustomer_Id(UUID branchOfficeId, Pageable pageable);
}
