package com.avocat.service;

import com.avocat.persistence.entity.Company;
import com.avocat.persistence.entity.Contract;
import com.avocat.persistence.entity.Customer;
import com.avocat.persistence.entity.process.Area;
import com.avocat.persistence.entity.process.Process;
import com.avocat.persistence.types.CompanyTypes;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

@SpringBootTest
class ProcessServiceTest {

    @Autowired
    private ProcessService processService;

    @Test
    void shouldCreteProcessAndWillReturn201() {

        var area = new Area();
        area.setId(UUID.fromString("6b51d71c-27f2-4d19-853a-b2fbfe88a9ef"));
        area.setName("Trabalhista");
        area.setCustomerId(UUID.fromString("eb2efb3d-f70d-46ee-99dc-8ba7c6a5f7e3"));
        area.setActive(true);

        var contract = new Contract();
        contract.setId(UUID.fromString("e8fa4fdb-f33b-4bc5-ab71-a6c2a5c7f4e0"));
        contract.setName("Filipe e Edson Corretores Associados Ltda");

        var principal = new Company();
        principal.setId(UUID.fromString("fffc0201-e0d6-463f-a004-8e76856a15fd"));
        principal.setName("4569f4d9-f");

        var contrary = new Company();
        contrary.setId(UUID.fromString("da42a40c-4ad5-4305-9808-7bdb92a26aee"));
        contrary.setName("95807764-8");

        var customer = new Customer();
        customer.setId(UUID.fromString("d5d7da4a-4520-446e-9a6a-aaf4b76f803f"));
        customer.setFullName("Martins Associados");
        customer.setOfficeName("Martins");

        var process = new Process.Builder("1728309-61.0964.XFg.0091", null, null, "Réu", "", area, contract).build();

        process.setPrincipal(principal);
        process.setPrincipalPaper(contrary.getName());

        process.setContrary(contrary);
        process.setContraryPaper(contrary.getName());

        process.setBranchOffice(UUID.fromString("65344a5e-81ce-4eb3-b16b-955d26b73ede"));

        process.setCustomer(customer);

        var result = processService.create(process);

        Assertions.assertNotNull(result.getId());
        Assertions.assertEquals(result.getArea().getId(), UUID.fromString("6b51d71c-27f2-4d19-853a-b2fbfe88a9ef"));
        Assertions.assertEquals(result.getContract().getId(), UUID.fromString("e8fa4fdb-f33b-4bc5-ab71-a6c2a5c7f4e0"));
        Assertions.assertEquals(result.getProcessNumber(), "1728309-61.0964.XFg.0091");
        Assertions.assertEquals(result.getCustomer().getId(), UUID.fromString("d5d7da4a-4520-446e-9a6a-aaf4b76f803f"));
        Assertions.assertEquals(result.getBranchOffice(), UUID.fromString("65344a5e-81ce-4eb3-b16b-955d26b73ede"));
    }

    private Process getProcess() {
        Process process = new Process();
        process.setId(UUID.randomUUID());
        process.setProcessNumber("1728309-61.0964.XFg.0091");
        return process;
    }
}