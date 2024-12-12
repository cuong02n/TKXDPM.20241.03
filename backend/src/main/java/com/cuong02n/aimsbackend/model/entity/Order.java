package com.cuong02n.aimsbackend.model.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "`order`")
public class Order extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long orderId;
    @ManyToOne
    @JoinColumn(name = "user_id")
    User user;
    boolean isRush = false;
    boolean isPaid = false;
}
