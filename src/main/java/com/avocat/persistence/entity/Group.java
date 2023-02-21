package com.avocat.persistence.entity;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.Assert;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.UUID;

@NoArgsConstructor
@Getter
@Data
@Entity
@Table(name = "groups")
@AttributeOverride(name = "id", column = @Column(name = "group_id", nullable = false))
public class Group extends AuditEntity {

    @NotEmpty(message = "invalid group description format")
    private String name;

    private Group(UUID id, String name, UUID branchOfficeRef) {
        this.id = id;
        this.name = name;
        this.branchOfficeRef = branchOfficeRef;
    }

    public static Group create(UUID id, String name, UUID branchOfficeRef) {
        Assert.notNull(branchOfficeRef, "field branchOffice can not be null");
        return new Group(id, name, branchOfficeRef);
    }

    public static Group from(Group source, Group target) {
        target.setName(source.getName());
        return target;
    }
}
