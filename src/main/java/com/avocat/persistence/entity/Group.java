package com.avocat.persistence.entity;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
    @Column(nullable = false, unique = true)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    private UserApp userApp;

    private Group(String name) {
        this.name = name;
    }

    public static Group create(String name) {
        return new Group(name);
    }

    public static Group from(Group updateGroup, Group resultGroup) {
        resultGroup.setName(updateGroup.getName());
        return resultGroup;
    }
}
