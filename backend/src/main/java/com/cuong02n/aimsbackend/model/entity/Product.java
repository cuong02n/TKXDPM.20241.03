package com.cuong02n.aimsbackend.model.entity;

import jakarta.persistence.*;
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
    private String price;

    private String additionalData;

    @Enumerated(EnumType.STRING)
    private ProductCategory category;

    @OneToMany(mappedBy = "product")
    List<ProductCart> productCarts;

    @ElementCollection(fetch = FetchType.EAGER)
    @Column(name = "media_url")
    List<String> mediaUrls;

    enum ProductCategory {
        CD, DVD, BOOK;
    }

}
