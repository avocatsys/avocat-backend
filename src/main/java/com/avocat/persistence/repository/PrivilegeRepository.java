package com.avocat.persistence.repository;

import com.avocat.persistence.entity.Privilege;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;
import java.util.UUID;

@Repository
public interface PrivilegeRepository extends JpaRepository<Privilege, UUID> {

    Set<Privilege> findByName(String name);

    Page<Privilege> findAll(Pageable pageable);
}
