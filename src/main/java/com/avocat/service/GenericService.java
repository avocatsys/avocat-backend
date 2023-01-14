package com.avocat.service;

import com.avocat.persistence.entity.AuditEntity;

import java.util.List;
import java.util.UUID;

public interface GenericService<T extends AuditEntity> {

    T create(T obj);

    void delete(UUID id);

    List<T> findAll();

    T findById(UUID id);
}
