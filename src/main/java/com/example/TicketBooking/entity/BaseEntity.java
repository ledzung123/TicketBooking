package com.example.TicketBooking.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

// Share audit fields with entities without creating a separate table.
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class) // Populate audit fields automatically.
@Setter
@Getter
public abstract class BaseEntity {
    @CreatedDate
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    private  LocalDateTime updatedAt;

    @CreatedBy
    @Column(name = "created_by", updatable = false) // Preserve the original creator.
    private String createdBy;

    @LastModifiedBy
    @Column(name = "updated_by")
    private String updatedBy;
}
