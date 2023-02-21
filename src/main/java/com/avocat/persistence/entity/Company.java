package com.avocat.persistence.entity;

import com.avocat.persistence.types.CompanyTypes;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "companies")
@AttributeOverride(name = "id", column = @Column(name = "company_id", nullable = false))
public class Company extends AuditEntity {

    @NotEmpty(message = "invalid company name format")
    @Column(name = "name")
    private String name;

    @NotEmpty(message = "invalid cpf or cnpj format")
    @Column(name = "cpf_cnpj")
    private String cpfCnpj;

    @NotEmpty(message = "invalid company billing email format")
    @Column(name = "billing_email")
    private String billingEmail;

    @Column(columnDefinition = "text", name = "description")
    private String description;

    @Column(name = "company_types")
    @Enumerated(EnumType.STRING)
    private CompanyTypes companyTypes;

    @Column(name = "state_registration")
    private String stateRegistration;

    @Column(name = "issue_day")
    private Integer issueDay;//dia da emissão

    @Column(name = "due_date")
    private Integer dueDate;//dia vencimento

    @Column(name = "maturity_term")
    private Integer maturityTerm;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "branch_office_id", referencedColumnName = "branch_office_id")
    private BranchOffice branchOffice;
}
