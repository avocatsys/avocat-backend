package com.avocat.persistence.repository;

import com.avocat.persistence.entity.AuditEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface GenericRepository<T extends AuditEntity> extends JpaRepository<T, UUID> {
}
