package com.avocat.persistence.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.br.CNPJ;
import org.hibernate.validator.constraints.br.CPF;

import javax.persistence.*;
import javax.validation.constraints.Email;

@NoArgsConstructor
@Data
@Entity
@Table(name = "branch_offices")
@AttributeOverride(name = "id", column = @Column(name = "branch_office_id", nullable = false))
public class BranchOffice extends AuditEntity {

    @Column(name = "corporate_name", nullable = false)
    private String corporteName;

    @Column(name = "branch_office_name", nullable = false)
    private String branchOfficeName;

    @Column(name = "code_office")
    private String codeOffice;

    @Column(name = "state_registration_name")
    private String stateRegistration;//inscrição estadual

    @CNPJ(message = "invalid cnpj format")
    @CPF(message = "invalid cpf format")
    @Column(name = "cpf_cnpj", nullable = false)
    private String cpfCnpj;

    @Column(name = "email", nullable = false)
    @Email(message = "invalid format email")
    private String email;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", referencedColumnName = "customer_id")
    private Customer customers;

    private BranchOffice(Builder builder) {
        this.corporteName = builder.corporteName;
        this.branchOfficeName = builder.branchOfficeName;
        this.codeOffice = builder.codeOffice;
        this.stateRegistration = builder.stateRegistration;
        this.cpfCnpj = builder.cpfCnpj;
        this.email = builder.email;
        this.customers = builder.customer;
    }

    public static class Builder {

        //mandatory
        private String cpfCnpj;
        private String email;
        private String corporteName;
        private String branchOfficeName;
        private Customer customer;

        //optional
        private String codeOffice;
        private String stateRegistration;//inscrição estadual

        public Builder(String cpfCnpj, String email, String corporteName,
                       String branchOfficeName, Customer customer) {
            this.cpfCnpj = cpfCnpj;
            this.email = email;
            this.corporteName = corporteName;
            this.branchOfficeName = branchOfficeName;
            this.customer = customer;
        }

        public Builder codeOffice(String codeOffice) {
            this.codeOffice = codeOffice;
            return this;
        }

        public Builder stateRegistration(String stateRegistration) {
            this.stateRegistration = stateRegistration;
            return this;
        }

        public BranchOffice build() {
            return new BranchOffice(this);
        }

    }

    public static BranchOffice from(BranchOffice source, BranchOffice target) {
        target.setBranchOfficeName(source.getBranchOfficeName());
        target.setCorporteName(source.getCorporteName());
        target.setCodeOffice(source.getCodeOffice());
        target.setCpfCnpj(source.getCpfCnpj());
        target.setEmail(source.getEmail());
        target.setStateRegistration(source.getStateRegistration());
        return target;
    }
}
