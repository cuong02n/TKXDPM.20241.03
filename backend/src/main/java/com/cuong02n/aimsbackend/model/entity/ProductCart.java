package com.cuong02n.aimsbackend.model.entity;

import jakarta.persistence.*;
import lombok.*;


@Entity
@Data
@EqualsAndHashCode(callSuper = false)
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
    private UserCart userCart;

    @Getter
    @Embeddable
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ProductCartKey {
        @Column(name = "product_id")
        private String productId;
        @Column(name = "user_email")
        private String userEmail;
    }
}
