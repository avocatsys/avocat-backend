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

    private Group(UUID id, String name) {
        this.id = id;
        this.name = name;
    }

    public static Group create(UUID id, String name) {
        return new Group(id, name);
    }

    public static Group from(Group source, Group target) {
        target.setName(source.getName());
        return target;
    }
}
