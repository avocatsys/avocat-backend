package com.avocat.persistence.entity;

import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.core.ApplicationContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.Assert;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@Slf4j
@Data
@MappedSuperclass
@EntityListeners({AuditingEntityListener.class})
public class AuditEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Transient
    @Autowired
    private ApplicationContext appContext;

    @Id
    @GeneratedValue
    private UUID id;

    @Column(name = "branch_office_ref", updatable = false, nullable = false)
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
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        //todo apos a criação da filial finalizar essa lógica.
        this.branchOffice = UUID.randomUUID();
        Assert.notNull(branchOffice, "field branchOffice can not be null");

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

