package com.cuong02n.aimsbackend.model.entity;

import jakarta.persistence.*;
import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
public class FavoriteProductUser extends BaseEntity {
    @EmbeddedId
    private WishListKey key;

    @ManyToOne
    @MapsId("userEmail")
    @JoinColumn(name = "user_email")
    private User user;

    @ManyToOne
    @MapsId("productId")
    @JoinColumn(name = "product_id")
    private Product product;

    @Embeddable
    @AllArgsConstructor
    @NoArgsConstructor
    public static class WishListKey {
        @Column(name = "user_email")
        String userEmail;
        @Column(name = "product_id")
        String productId;
    }
}
