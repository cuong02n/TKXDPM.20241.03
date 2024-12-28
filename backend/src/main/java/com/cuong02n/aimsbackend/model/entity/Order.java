package com.cuong02n.aimsbackend.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "`order`")
@Getter
@Setter
public class Order extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    Long orderId;

    @ManyToOne
    @JoinColumn(name = "user_email")
    @Setter
    User user;

    @OneToMany(mappedBy = "order",cascade = CascadeType.ALL)
    @Setter
    @Getter
    List<OrderProduct> orderProducts;

    private String address;

    private String phone;

    private String province;
    private String shippingInstruction;

    boolean isRush = false;
    int timeInMinute = 120;
}
