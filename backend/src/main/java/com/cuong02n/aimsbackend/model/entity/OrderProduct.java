package com.cuong02n.aimsbackend.model.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderProduct {
    @EmbeddedId
    private OrderProductKey key;

    @ManyToOne
    @MapsId("productId")
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne
    @MapsId("orderId")
    @JoinColumn(name = "order_id")
    private Order order;
    int quantity;
    @Embeddable
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class OrderProductKey {
        @Column(name = "product_id")
        private String productId;
        @Column(name = "order_id")
        private Long orderId;
    }
}
