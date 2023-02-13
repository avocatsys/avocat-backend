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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    private UserApp userApp;

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
