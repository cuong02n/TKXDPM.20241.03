package com.cuong02n.aimsbackend.model.entity;

import com.google.gson.annotations.Expose;
import jakarta.annotation.Nullable;
import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Getter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Timestamp;

@Getter
@MappedSuperclass
public class BaseEntity {
    @CreationTimestamp
    @Temporal(value = TemporalType.TIMESTAMP)
    @Column(name = "createdTime", updatable = false)
    @Nullable
    Timestamp createdTime;

    @UpdateTimestamp
    @Temporal(value = TemporalType.TIMESTAMP)
    @Column(name = "updatedTime")
    @Nullable
    Timestamp updatedTime;

}