package com.avocat.persistence.repository.process;


import com.avocat.persistence.entity.process.Area;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface AreaRepository extends JpaRepository<Area, UUID> {

   Page<Area> findAllByCustomerId(UUID customerId, Pageable pageable);

   Optional<Area> findByIdAndCustomerId(UUID areaId, UUID customerId);
}
