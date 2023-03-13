package com.avocat.persistence.entity.process;

import lombok.*;

import javax.persistence.*;
import java.util.UUID;

@NoArgsConstructor
@Data
@Entity
@Table(name = "areas")
public class Area {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(name = "name")
    private String name;

    @Column(name = "active")
    protected boolean active = true;

    @Column(name = "customer_id")
    private UUID customerId;
}
