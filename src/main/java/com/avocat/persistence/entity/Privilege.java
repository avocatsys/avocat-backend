package com.avocat.persistence.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

@NoArgsConstructor
@Data
@Entity
@Table(name = "privileges")
@AttributeOverride(name = "id", column = @Column(name = "privilege_id", nullable = false))
public class Privilege extends AuditEntity {

    @NotEmpty(message = "invalid privilege description format")
    private String name;

    public Privilege(String name) {
        this.name = name;
    }

    public static Privilege create(String name){
        return new Privilege(name);
    }

    public static Privilege from(Privilege resource, Privilege target) {
        target.setName(resource.getName());
        return target;
    }
}
