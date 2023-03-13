package com.avocat.persistence.repository.process;


import com.avocat.persistence.entity.process.Action;
import com.avocat.persistence.entity.process.Area;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ActionRepository extends JpaRepository<Action, UUID> {

   Page<Action> findAllByCustomerId(UUID customerId, Pageable pageable);

   Optional<Action> findByIdAndCustomerId(UUID areaId, UUID customerId);
}
