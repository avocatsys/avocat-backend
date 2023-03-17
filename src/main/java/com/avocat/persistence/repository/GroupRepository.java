package com.avocat.persistence.repository;

import com.avocat.persistence.entity.Group;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface GroupRepository extends JpaRepository<Group, UUID> {

    Page<Group> findAllByCustomerId(UUID customerId, Pageable pageable);
}
