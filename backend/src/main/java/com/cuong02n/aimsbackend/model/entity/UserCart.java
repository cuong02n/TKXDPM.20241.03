package com.cuong02n.aimsbackend.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Entity
@RequiredArgsConstructor
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
public class UserCart extends BaseEntity {
    @Id
    @Column(name = "user_email")
    private String userEmail;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_email")
    @MapsId
    private User user;

    @OneToMany(mappedBy = "userCart")
    @Getter
    private List<ProductCart> productCarts;

}

