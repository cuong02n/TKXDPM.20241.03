package com.cuong02n.aimsbackend.model.entity;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderProduct extends BaseEntity{
    @EmbeddedId
    private OrderProductKey key;

    @ManyToOne
    @MapsId("productId")
    @JoinColumn(name = "product_id")
    @Getter
    private Product product;

    @ManyToOne
    @MapsId("orderId")
    @JoinColumn(name = "order_id")
    private Order order;
    @Getter
    int quantity;
    @Embeddable
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class OrderProductKey {
        @Column(name = "product_id")
        private long productId;
        @Column(name = "order_id")
        private Long orderId;
    }
}
