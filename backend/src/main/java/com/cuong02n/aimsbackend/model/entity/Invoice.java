package com.cuong02n.aimsbackend.model.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Setter;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
public class Invoice extends BaseEntity {
    @Id
    @Column(name = "order_id")
    Long orderId;

    @OneToOne
    @JoinColumn(name = "order_id")
    @MapsId
    @Setter
    private Order order;

    @Setter
    private boolean isPaid = false;
    @Setter
    private long shippingFee;
    @Setter
    private long totalAmountWithoutVAT; // tong tien hang ko chua shipping fee;
    @Setter
    private long totalAmountIncludeVAT;
    @Setter
    private long totalAmountIncludeShippingFee;



}
