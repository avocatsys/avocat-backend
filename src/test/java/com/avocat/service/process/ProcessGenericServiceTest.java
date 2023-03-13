package com.avocat.service.process;

import com.avocat.persistence.entity.process.Area;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

@SpringBootTest
class ProcessGenericServiceTest {

    @Autowired
    private ProcessGenericService<Area> processGenericService;

    @Test
    void create() {
        var customerId = UUID.fromString("d5d7da4a-4520-446e-9a6a-aaf4b76f803f");
        var name = UUID.randomUUID().toString().substring(0, 10);

        var area = new Area();
        area.setCustomerId(customerId);
        area.setName(name);

        var result = processGenericService.create(area);

        Assertions.assertEquals(result.getCustomerId(), customerId);
        Assertions.assertEquals(result.getName(), name);
    }
}