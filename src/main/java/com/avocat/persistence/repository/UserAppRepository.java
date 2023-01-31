package com.avocat.persistence.repository;

import com.avocat.persistence.entity.UserApp;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserAppRepository extends JpaRepository<UserApp, UUID> {

    Optional<UserApp> findByUsername(String email);

    Page<UserApp> findAllByBranchOffice_Id(@Param("branchOfficeId") UUID branchOfficeId, Pageable pageable);

    Optional<UserApp> findByUsernameAndBranchOffice_Id(@Param("email") String email, @Param("branchOfficeId") UUID branchOfficeId);
}
