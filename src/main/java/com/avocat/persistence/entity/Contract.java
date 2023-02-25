package com.avocat.persistence.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDate;
import java.util.UUID;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "contracts")
@AttributeOverride(name = "id", column = @Column(name = "contract_id", nullable = false))
public class Contract extends AuditEntity {

    @NotEmpty(message = "invalid contract name format")
    @Column(name = "name")
    private String name;

    @NotEmpty(message = "invalid annotation billing format")
    @Column(name = "annotation_billing")
    private String annotationBilling;

    @NotEmpty(message = "invalid general note format")
    @Column(name = "general_note")
    private String generalNote;

    @Column(name = "adjustment_date")
    private LocalDate adjustmentDate;

    @Column(name = "closing_date")
    private LocalDate closingDate;

    @ManyToOne
    @JoinColumn(name = "company_id", referencedColumnName = "company_id")
    private Company company;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "customer_id", referencedColumnName = "customer_id")
    private Customer customer;

    public static Contract from(Contract contractResult, Contract contract, UUID branchOfficeId) {
        contractResult.setBranchOffice(branchOfficeId);
        contractResult.setName(contract.getName());
        contractResult.setAnnotationBilling(contract.getAnnotationBilling());
        contractResult.setGeneralNote(contract.getGeneralNote());
        contractResult.setAdjustmentDate(contract.getAdjustmentDate());
        contractResult.setClosingDate(contract.getClosingDate());
        contractResult.setCompany(contract.getCompany());
        contractResult.setCustomer(contract.getCustomer());
        return contractResult;
    }
}
