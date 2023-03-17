package com.avocat.controller.process.dto;

import com.avocat.persistence.entity.process.*;

import java.time.LocalDate;
import java.util.UUID;

public record ProcessDto(
        String processNumber,
        String auxiliaryCode,
        String internalObservation,
        String closingObservation,
        String customerObservation,
        String financialObservation,
        String detailObject,
        LocalDate dateEntry,
        LocalDate distributionDate,
        String principalPaper,
        String contraryPaper,
        Area area,
        Action action,
        ProceduralPhase proceduralPhase,
        Rite rite,
        JudicialDistrict judicialDistrict,
        Forum forum,
        LegalBranch legalBranch,
        UUID principalId,
        UUID contraryId,
        UUID contractId
) {
}
