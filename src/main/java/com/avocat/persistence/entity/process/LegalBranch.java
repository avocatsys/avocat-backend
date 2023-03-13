package com.avocat.persistence.entity.process;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "legal_branch")
@AttributeOverride(name = "id", column = @Column(name = "legal_branch_id", nullable = false))
public class LegalBranch extends BaseProcess {
}
