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
public class Privilege extends AuditEntity{

    @Id
    @GeneratedValue
    @Column(name = "privilege_id", nullable = false)
    private UUID id;

    @NotEmpty(message = "invalid privilege description format")
    @Column(nullable = false, unique = true)
    private String name;
}
