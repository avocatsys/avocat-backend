package com.avocat.persistence.repository;

import com.avocat.persistence.entity.Company;
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
public interface CompanyRepository extends JpaRepository<Company, UUID> {

    Optional<Company> findByIdAndActiveTrue(UUID companyId);

    Page<Company> findAllByActiveTrueAndCustomer_Id(UUID branchOfficeId, Pageable pageable);

    @Modifying
    @Query("update Company c set c.active = false where c.id = :companyId and c.branchOffice = :branchOfficeId")
    void inactiveCompany(@Param("companyId") UUID companyId, @Param("branchOfficeId") UUID branchOfficeId);
}
