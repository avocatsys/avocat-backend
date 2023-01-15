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
}
