package com.cuong02n.aimsbackend.model.entity;

import com.cuong02n.aimsbackend.converter.ProductHashMapConverter;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.HashMap;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
public class Product extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private String description;
    private String quantity;
    private String price;
    @Convert(converter = ProductHashMapConverter.class)
    @Column(columnDefinition = "json")
    private HashMap<String,String> additionalData;

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
