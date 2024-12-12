package com.cuong02n.aimsbackend.model.entity;


import jakarta.persistence.Entity;

//@Entity
public class Invoice extends BaseEntity{
    private Order order;
    private long shippingFee;
    private long totalAmount;

}
