package com.avocat.persistence.entity;

import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@Slf4j
@Data
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class AuditEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    private UUID id;

    //todo Depois implementar um mecanismo de auditar e gararantir que esse campo seja preenchido ref: https://www.baeldung.com/database-auditing-jpa
    @Column(name = "branch_office_ref", updatable = false)
    protected UUID branchOffice;

    @Column(name = "created_date", updatable = false)
    protected LocalDateTime createdDate;

    @Column(name = "removed_date", updatable = false)
    protected LocalDateTime removedDate;

    @Column(name = "updated_date")
    protected LocalDateTime updatedDate;

    @Column(name = "created_by")
    @CreatedBy
    protected String createdBy;

    @Column(name = "modified_by")
    @LastModifiedBy
    protected String modifiedBy;

    @PrePersist
    public void onPrePersist() {
        this.createdDate = LocalDateTime.now();
        log.info("created " + this.createdDate);
    }

    @PreUpdate
    public void onPreUpdate() {
        this.updatedDate = LocalDateTime.now();
        log.info("updated " + this.updatedDate);
    }

    @PreRemove
    public void onPreRemove() {
        this.removedDate = LocalDateTime.now();
        log.info("removed " + this.removedDate);
    }
}

