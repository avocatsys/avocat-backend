package com.avocat.service;

import com.avocat.persistence.entity.Company;
import com.avocat.persistence.entity.Contract;
import com.avocat.persistence.entity.Customer;
import com.avocat.persistence.entity.process.Area;
import com.avocat.persistence.entity.process.Process;
import com.avocat.persistence.repository.process.ProcessRepository;
import com.avocat.persistence.types.CompanyTypes;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;

@ExtendWith(MockitoExtension.class)
class ProcessServiceTest {

    @Mock
    private ProcessRepository processRepository;

    @InjectMocks
    private ProcessService processService;

    @Test
    void shouldCreteProcessAndWillReturn201() {

        var customer = Customer.getInstanceWithOutUser("", "", "");

        var companyPrincipal = new Company.Builder("Teste Integration", "57764148067", CompanyTypes.JURIDICA, UUID.randomUUID(), customer).build();
        var companyContrary = new Company.Builder("Teste Integration", "57764148067", CompanyTypes.JURIDICA, UUID.randomUUID(), customer).build();

        var area = new Area();
        var contract = new Contract();

        var newProcess = new Process.Builder("1728309-61.0964.XFg.0091", companyPrincipal, companyContrary, "Réu", "", area, contract).build();

        Mockito.when(processRepository.save(newProcess)).thenReturn(getProcess());

        var result = processService.create(newProcess);

        Assertions.assertEquals("1728309-61.0964.XFg.0091", result.getProcessNumber());
    }

    private Process getProcess() {
        Process process = new Process();
        process.setId(UUID.randomUUID());
        process.setProcessNumber("1728309-61.0964.XFg.0091");
        return process;
    }
}