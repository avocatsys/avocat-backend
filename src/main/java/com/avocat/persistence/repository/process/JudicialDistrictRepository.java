package com.avocat.persistence.repository.process;

import com.avocat.persistence.entity.process.JudicialDistrict;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface JudicialDistrictRepository extends JpaRepository<JudicialDistrict, UUID> {

    Page<JudicialDistrict> findAllByCustomerId(UUID forumId, Pageable pageable);

    Optional<JudicialDistrict> findByIdAndCustomerId(UUID forumId, UUID customerId);
}
