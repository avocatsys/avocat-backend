package com.avocat.persistence.entity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.util.Assert;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import java.util.UUID;

@Setter
@Getter
@Entity
@Table(name = "groups")
@AttributeOverride(name = "id", column = @Column(name = "group_id", nullable = false))
public class Group extends AuditEntity {

    @NotEmpty(message = "invalid group description format")
    private String name;

    private Group(UUID id, String name, UUID branchOfficeId) {
        this.id = id;
        this.name = name;
        this.branchOffice = branchOfficeId;
    }

    public static Group create(UUID id, String name, UUID branchOfficeId) {
        Assert.notNull(branchOfficeId, "field branchOffice can not be null");
        return new Group(id, name, branchOfficeId);
    }

    public static Group from(Group source, Group target) {
        target.setName(source.getName());
        return target;
    }
}
