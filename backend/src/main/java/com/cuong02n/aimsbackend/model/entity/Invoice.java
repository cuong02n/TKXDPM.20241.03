package com.cuong02n.aimsbackend.model.entity;


import jakarta.persistence.*;

@Entity
public class Invoice extends BaseEntity {
    @Id
    @Column(name = "order_id")
    Long orderId;

    @OneToOne
    @JoinColumn(name = "order_id")
    @MapsId
    private Order order;
    private long shippingFee;
    private long totalAmount;
}
