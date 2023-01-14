package com.avocat.persistence.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.UUID;

@NoArgsConstructor
@Data
@Entity
@Table(name = "groups")
public class Group extends AuditEntity {

    @Id
    @GeneratedValue
    @Column(name = "group_id", nullable = false)
    private UUID id;

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
