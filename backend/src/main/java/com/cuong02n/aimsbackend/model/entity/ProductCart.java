package com.cuong02n.aimsbackend.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;


@Entity
@Data
@EqualsAndHashCode(callSuper = true)
public class ProductCart extends BaseEntity {
    @EmbeddedId
    private ProductCartKey key;

    /**
     * Should be > 0
     */
    private int quantity;

    @ManyToOne
    @MapsId("productId")
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne
    @MapsId("userEmail")
    @JoinColumn(name = "user_email")
    private User user;

    @Embeddable
    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    public static class ProductCartKey {
        @Column(name = "product_id")
        private long productId;
        @Column(name = "user_email")
        private String userEmail;
    }
}
