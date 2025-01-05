package com.cuong02n.aimsbackend.model.entity;

import com.cuong02n.aimsbackend.converter.ProductHashMapConverter;
import com.google.gson.annotations.Expose;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.HashMap;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
public class Product extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Getter
    private String name;
    private String description;
    private int available;
    private int price;
    private double weight;
    @Convert(converter = ProductHashMapConverter.class)
    @Column(columnDefinition = "json")
    private HashMap<String, String> additionalData;

    @Enumerated(EnumType.STRING)
    private ProductCategory category;

    private boolean isSupportedRush = false;
//    @OneToMany(mappedBy = "product")
//    List<ProductCart> productCarts;

    @ElementCollection(fetch = FetchType.EAGER)
    @Column(name = "media_url")
    List<String> mediaUrls;

    public enum ProductCategory {
        CD, DVD, BOOK
    }

}
