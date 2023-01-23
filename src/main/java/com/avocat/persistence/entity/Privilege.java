package com.avocat.persistence.entity;

import com.avocat.service.PrivilegeService;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.UUID;

@NoArgsConstructor
@Data
@Entity
@Table(name = "privileges")
@AttributeOverride(name = "id", column = @Column(name = "privilege_id", nullable = false))
public class Privilege extends AuditEntity {

    @NotEmpty(message = "invalid privilege description format")
    @Column
    private String name;

    public Privilege(String name) {
        this.name = name;
    }

    public static Privilege create(String name){
        return new Privilege(name);
    }
}
