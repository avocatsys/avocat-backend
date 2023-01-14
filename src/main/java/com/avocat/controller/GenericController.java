package com.avocat.controller;

import com.avocat.persistence.entity.AuditEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
public interface GenericController<T extends AuditEntity> {

    @PostMapping
    ResponseEntity<T> create(@RequestBody T obj);

    @DeleteMapping("/{id}")
    void delete(@PathVariable("id") UUID id);

    @GetMapping
    ResponseEntity<List<T>> findAll();//todo trocar para pageble

    @GetMapping("/{id}")
    ResponseEntity<T> findById(@PathVariable("id") UUID id);
}
