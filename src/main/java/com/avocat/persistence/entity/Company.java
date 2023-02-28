package com.avocat.persistence.entity;

import com.avocat.persistence.types.CompanyTypes;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.UUID;

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
    @Size(min = 14, max = 20)
    private String cpfCnpj;

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

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "customer_id", referencedColumnName = "customer_id")
    private Customer customer;

    public Company(Builder builder) {
        this.name = builder.name;
        this.cpfCnpj = builder.cpfCnpj;
        this.billingEmail = builder.billingEmail;
        this.description = builder.description;
        this.companyTypes = builder.companyTypes;
        this.stateRegistration = builder.stateRegistration;
        this.issueDay = builder.issueDay;
        this.dueDate = builder.dueDate;
        this.maturityTerm = builder.maturityTerm;
        this.customer = builder.customer;
        this.branchOffice = builder.branchOfficeRef;
    }

    public static Company from(Company companyResult, Company company, UUID branchOfficeId) {
        companyResult.setBranchOffice(branchOfficeId);
        companyResult.setName(company.getName());
        companyResult.setCpfCnpj(company.getCpfCnpj());
        companyResult.setBillingEmail(companyResult.getBillingEmail());
        companyResult.setDescription(company.getDescription());
        companyResult.setCompanyTypes(company.getCompanyTypes());
        companyResult.setStateRegistration(company.getStateRegistration());
        companyResult.setIssueDay(company.getIssueDay());
        companyResult.setDueDate(companyResult.getDueDate());
        companyResult.setMaturityTerm(companyResult.getMaturityTerm());
        companyResult.setCustomer(company.getCustomer());

        return companyResult;
    }

    public static class Builder {

        //mandatory
        private final String name;
        private final String cpfCnpj;
        private final CompanyTypes companyTypes;
        private final UUID branchOfficeRef;
        private final Customer customer;

        //optional
        private String billingEmail;
        private String description;
        private String stateRegistration;
        private Integer issueDay;
        private Integer dueDate;
        private Integer maturityTerm;

        public Builder(String name, String cpfCnpj, CompanyTypes companyTypes, UUID branchOfficeRef, Customer customer) {
            this.name = name;
            this.cpfCnpj = cpfCnpj;
            this.companyTypes = companyTypes;
            this.branchOfficeRef = branchOfficeRef;
            this.customer = customer;
        }

        public Builder billingEmail(String billingEmail) {
            this.billingEmail = billingEmail;
            return this;
        }

        public Builder description(String description) {
            this.description = description;
            return this;
        }

        public Builder stateRegistration(String stateRegistration) {
            this.stateRegistration = stateRegistration;
            return this;
        }

        public Builder issueDay(Integer issueDay) {
            this.issueDay = issueDay;
            return this;
        }

        public Builder dueDate(Integer dueDate) {
            this.dueDate = dueDate;
            return this;
        }

        public Builder maturityTerm(Integer maturityTerm) {
            this.maturityTerm = maturityTerm;
            return this;
        }

        public Company build() {
            return new Company(this);
        }
    }
}
