package com.cuong02n.aimsbackend.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "`order`")
@Getter
public class Order extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    Long orderId;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @Setter
    User user;

    @OneToMany(mappedBy = "order")
    @Setter
    @Getter
    List<OrderProduct> orderProducts;
    boolean isRush = false;
    boolean isPaid = false;

    private String address;

    private String phone;

    private String province;
    private String shippingInstruction;
    private long amount;
}
