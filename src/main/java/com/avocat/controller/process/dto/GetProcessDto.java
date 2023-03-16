package com.avocat.controller.process.dto;

import com.avocat.persistence.entity.process.Process;

import java.util.UUID;

public record GetProcessDto(
        UUID processId,
        String processNumber,
        String principalPaper,
        String contraryPaper,
        String area,
        String contractName
) {
    private GetProcessDto(Process process) {
        this(
                process.getId(),
                process.getProcessNumber(),
                process.getPrincipalPaper(),
                process.getContraryPaper(),
                process.getArea().getName(),
                process.getContract().getName()
        );
    }

    public static GetProcessDto from(Process process) {
        return new GetProcessDto(process);
    }
}
