package com.avocat.persistence.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.UUID;

@NoArgsConstructor
@Data
@Entity
@Table(name = "privileges")
@AttributeOverride(name = "id", column = @Column(name = "privilege_id", nullable = false))
public class Privilege extends AuditEntity {

    @NotEmpty(message = "invalid privilege description format")
    @Column(nullable = false, unique = true)
    private String name;
}
