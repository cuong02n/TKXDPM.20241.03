package com.cuong02n.aimsbackend.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
public class Product extends BaseEntity {
    @Id
    private String id;
    private String name;
    private String description;
    private String quantity;
    private String originalPrice;

    @OneToMany(mappedBy = "product")
    List<ProductCart> productCarts;
}
