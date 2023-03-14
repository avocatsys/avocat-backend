package com.avocat.service;

import com.avocat.persistence.entity.process.Process;
import com.avocat.persistence.repository.process.ProcessRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class ProcessService {

    @Autowired
    private ProcessRepository processRepository;

    @Transactional
    public Process create(Process process) {
        return processRepository.save(process);
    }
}
