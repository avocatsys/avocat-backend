package com.avocat.service.generic;

import com.avocat.exceptions.ResourceNotFoundException;
import com.avocat.persistence.entity.AuditEntity;
import com.avocat.persistence.repository.GenericRepository;
import org.springframework.beans.factory.annotation.Autowired;

import javax.transaction.Transactional;
import java.util.List;
import java.util.UUID;

public abstract class GenericService<T extends AuditEntity> {

    @Autowired
    private GenericRepository<T> genericRepository;

    @Transactional
    public T create(T obj) {
        return genericRepository.save(obj);
    }

    public abstract T update(UUID id, T obj);

    @Transactional
    public void delete(UUID id) {
        var result = genericRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("resource not found"));
        genericRepository.delete(result);
    }

    public List<T> findAll() {
        return genericRepository.findAll();
    }

    public T findById(UUID id){
        return genericRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("resource not found"));
    }
}
