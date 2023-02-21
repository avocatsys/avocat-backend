package com.avocat.persistence.repository;

import com.avocat.persistence.entity.Group;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface GroupRepository extends PagingAndSortingRepository<Group, UUID> {

    Page<Group> findAllByBranchOffice(UUID branchOfficeId, Pageable pageable);
}
