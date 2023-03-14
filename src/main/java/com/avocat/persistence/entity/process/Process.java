package com.avocat.persistence.entity.process;

import com.avocat.persistence.entity.AuditEntity;
import com.avocat.persistence.entity.Company;
import com.avocat.persistence.entity.Contract;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "process")
@AttributeOverride(name = "id", column = @Column(name = "process_id", nullable = false))
public class Process extends AuditEntity {

    @Column(name = "process_number")
    private String processNumber;

    @Column(name = "auxiliary_code")
    private String auxiliaryCode;

    @Column(name = "company_principal_id")
    private Company principal;

    @Column(name = "company_contrary_id")
    private Company contrary;

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

    @Column(name = "principal_paper_name")
    private String principalPaper;

    @Column(name = "contraty_paper_name")
    private String contraryPaper;

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
    @JoinColumn(name = "contract_id", referencedColumnName = "contract_id", nullable = false)
    private Contract contract;
}
