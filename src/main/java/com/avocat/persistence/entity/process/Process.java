package com.avocat.persistence.entity.process;

import com.avocat.persistence.entity.AuditEntity;
import com.avocat.persistence.entity.Company;
import com.avocat.persistence.entity.Contract;
import com.avocat.persistence.entity.Customer;
import com.avocat.persistence.types.MessageSystem;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.util.Assert;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDate;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "process")
@AttributeOverride(name = "id", column = @Column(name = "process_id", nullable = false))
public class Process extends AuditEntity {

    @NotEmpty(message = "invalid format process number")
    @Column(name = "process_number")
    private String processNumber;

    @Column(name = "auxiliary_code")
    private String auxiliaryCode;

    @Column(name = "internal_observation")
    private String internalObservation;

    @Column(name = "closing_observation")
    private String closingObservation;

    @Column(name = "customer_observation")
    private String customerObservation;

    @Column(name = "financial_observation")
    private String financialObservation;

    @Column(name = "detail_object")
    private String detailObject;

    @Column(name = "date_entry")
    private LocalDate dateEntry;

    @Column(name = "distribution_date")
    private LocalDate distributionDate;

    @NotEmpty(message = "invalid format principal name")
    @Column(name = "principal_paper_name")
    private String principalPaper;

    @NotEmpty(message = "invalid format contrary name")
    @Column(name = "contrary_paper_name")
    private String contraryPaper;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_principal_id")
    private Company principal;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_contrary_id")
    private Company contrary;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "area_id")
    private Area area;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "action_id")
    private Action action;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "phase_id")
    private ProceduralPhase proceduralPhase;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "rite_id")
    private Rite rite;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "judicial_district_id")
    private JudicialDistrict judicialDistrict;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "forum_id")
    private Forum forum;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "legal_branch_id")
    private LegalBranch legalBranch;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "contract_id", referencedColumnName = "contract_id")
    private Contract contract;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", referencedColumnName = "customer_id")
    private Customer customer;

    private Process(Builder builder) {

        this.branchOffice = builder.contract.getBranchOffice();

        Assert.notNull(branchOffice, MessageSystem.BRANCH_OFFICE_NOT_CAN_BE_NULL.getMessage());

        this.processNumber = builder.processNumber;
        this.principal = builder.principal;
        this.contrary = builder.contrary;
        this.principalPaper = builder.principalPaper;
        this.contraryPaper = builder.contraryPaper;
        this.area = builder.area;
        this.contract = builder.contract;
        this.customer = builder.customer;

        //optional
        this.auxiliaryCode = builder.auxiliaryCode;
        this.internalObservation = builder.internalObservation;
        this.closingObservation = builder.closingObservation;
        this.customerObservation = builder.customerObservation;
        this.financialObservation = builder.financialObservation;
        this.detailObject = builder.detailObject;
        this.dateEntry = builder.dateEntry;
        this.distributionDate = builder.distributionDate;
        this.action = builder.action;
        this.proceduralPhase = builder.proceduralPhase;
        this.rite = builder.rite;
        this.judicialDistrict = builder.judicialDistrict;
        this.forum = builder.forum;
        this.legalBranch = builder.legalBranch;
    }

    public static class Builder {

        //mandatory
        private final String processNumber;
        private final Company principal;
        private final Company contrary;
        private final String principalPaper;
        private final String contraryPaper;
        private final Area area;
        private final Contract contract;
        private final Customer customer;

        //optional
        private String auxiliaryCode;
        private String internalObservation;
        private String closingObservation;
        private String customerObservation;
        private String financialObservation;
        private String detailObject;
        private LocalDate dateEntry;
        private LocalDate distributionDate;
        private Action action;
        private ProceduralPhase proceduralPhase;
        private Rite rite;
        private JudicialDistrict judicialDistrict;
        private Forum forum;
        private LegalBranch legalBranch;

        public Builder(
                String processNumber,
                Company principal,
                Company contrary,
                String principalPaper,
                String contraryPaper,
                Area area,
                Contract contract,
                Customer customer) {
            this.processNumber = processNumber;
            this.principal = principal;
            this.contrary = contrary;
            this.principalPaper = principalPaper;
            this.contraryPaper = contraryPaper;
            this.area = area;
            this.contract = contract;
            this.customer = customer;

            Assert.notNull(contract, MessageSystem.CONTRACT_NOT_CAN_BE_NULL.getMessage());
            Assert.notNull(customer, MessageSystem.CUSTOMER_NOT_CAN_BE_NULL.getMessage());
            Assert.notNull(processNumber, MessageSystem.PROCESS_NOT_CAN_BE_NULL.getMessage());
            Assert.notNull(area, MessageSystem.PROCESS_AREA_NOT_CAN_BE_NULL.getMessage());
            Assert.notNull(principal, MessageSystem.PROCESS_NEEDS_TO_HAVE_MAIN_PART.getMessage());
            Assert.notNull(contrary, MessageSystem.PROCESS_MUST_HAVE_OPPOSING_PARTY.getMessage());
        }

        public Builder auxiliaryCode(String auxiliaryCode) {
            this.auxiliaryCode = auxiliaryCode;
            return this;
        }

        public Builder internalObservation(String internalObservation) {
            this.internalObservation = internalObservation;
            return this;
        }

        public Builder closingObservation(String closingObservation) {
            this.closingObservation = closingObservation;
            return this;
        }

        public Builder customerObservation(String customerObservation) {
            this.customerObservation = customerObservation;
            return this;
        }

        public Builder financialObservation(String financialObservation) {
            this.financialObservation = financialObservation;
            return this;
        }

        public Builder detailObject(String detailObject) {
            this.detailObject = detailObject;
            return this;
        }

        public Builder dateEntry(LocalDate dateEntry) {
            this.dateEntry = dateEntry;
            return this;
        }

        public Builder distributionDate(LocalDate distributionDate) {
            this.distributionDate = distributionDate;
            return this;
        }

        public Builder action(Action action) {
            this.action = action;
            return this;
        }

        public Builder proceduralPhase(ProceduralPhase proceduralPhase) {
            this.proceduralPhase = proceduralPhase;
            return this;
        }

        public Builder rite(Rite rite) {
            this.rite = rite;
            return this;
        }

        public Builder forum(Forum forum) {
            this.forum = forum;
            return this;
        }

        public Builder judicialDistrict(JudicialDistrict judicialDistrict) {
            this.judicialDistrict = judicialDistrict;
            return this;
        }

        public Builder legalBranch(LegalBranch legalBranch) {
            this.legalBranch = legalBranch;
            return this;
        }

        public Process build() {
            return new Process(this);
        }
    }
}
