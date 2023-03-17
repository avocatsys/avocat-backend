package com.avocat.persistence.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.util.Assert;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.UUID;

@NoArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "groups")
public class Group {

    @Id
    @GeneratedValue
    @Column(name = "group_id")
    private UUID id;

    @NotEmpty(message = "invalid group description format")
    private String name;

    @Column(name = "customer_id")
    private UUID customerId;

    private Group(UUID id, String name, UUID customerId) {
        this.id = id;
        this.name = name;
        this.customerId = customerId;
    }

    public static Group create(UUID id, String name, UUID customerId) {
        Assert.notNull(customerId, "field customerId can not be null");
        return new Group(id, name, customerId);
    }

    public static Group from(Group source, Group target) {
        target.setName(source.getName());
        return target;
    }
}
