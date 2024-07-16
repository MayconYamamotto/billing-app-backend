package br.com.billing.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Data
@SuperBuilder
@MappedSuperclass
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseEntity {

    @Column(name = "created_by", nullable = false, updatable = false)
    @CreatedBy
    protected String createdBy;

    @Column(name = "created_date", nullable = false, updatable = false)
    @CreatedDate
    protected LocalDateTime createdDate;

    @Column(name = "last_modified_by")
    @LastModifiedBy
    protected String lastModifiedBy;

    @Column(name = "last_modified_date")
    @LastModifiedDate
    protected LocalDateTime lastModifiedDate;
}
