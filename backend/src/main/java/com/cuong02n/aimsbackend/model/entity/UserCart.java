package com.cuong02n.aimsbackend.model.entity;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Entity
@RequiredArgsConstructor
@EqualsAndHashCode(callSuper=false)
public class UserCart extends BaseEntity{
    @Id
    @Column(name = "user_email")
    private String userEmail;

    @OneToOne
    @MapsId
    @JoinColumn(name = "user_email")
    private User user;

    @OneToMany(mappedBy = "userCart")
    private List<ProductCart> productCarts;

}

