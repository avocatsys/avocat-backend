package com.avocat.controller;

import com.avocat.persistence.entity.AuditEntity;
import com.avocat.service.GenericService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.UUID;

public class GenericControllerImpl<T extends AuditEntity> implements GenericController<T> {

    @Autowired
    private GenericService<T> genericService;

    @Override
    public ResponseEntity<T> create(T obj) {
        return ResponseEntity.status(HttpStatus.CREATED).body(genericService.create(obj));
    }

    @Override
    public void delete(UUID id) {
        genericService.delete(id);
    }

    @Override
    public ResponseEntity<List<T>> findAll() {
        return ResponseEntity.status(HttpStatus.OK).body(genericService.findAll());
    }

    @Override
    public ResponseEntity findById(UUID id) {
        return ResponseEntity.status(HttpStatus.OK).body(genericService.findById(id));
    }
}
