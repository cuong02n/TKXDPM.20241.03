package com.cuong02n.aimsbackend.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
public class Product extends BaseEntity{
    @Id
    private String id;
}
