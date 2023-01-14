package com.avocat.service;

import com.avocat.exceptions.ResourceNotFoundException;
import com.avocat.persistence.entity.AuditEntity;
import com.avocat.persistence.repository.GenericRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class GenericServiceImpl<T extends AuditEntity> implements GenericService<T> {

    @Autowired
    private GenericRepository<T> genericRepository;

    @Override
    public T create(T obj) {
        return genericRepository.save(obj);
    }

    @Override
    public void delete(UUID id) {
        var result = genericRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("resource not found"));
        genericRepository.delete(result);
    }

    @Override
    public List<T> findAll() {
        return genericRepository.findAll();
    }

    @Override
    public T findById(UUID id) {
        return genericRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("resource not found"));
    }
}
