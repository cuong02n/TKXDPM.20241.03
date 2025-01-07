package com.cuong02n.aimsbackend.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
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

    @NotBlank(message = "Address is required")
    @Size(max = 100, message = "Address must not exceed 100 characters")
    @Pattern(regexp = "^[a-zA-Z0-9/ ]+$", message = "Address can only contain letters, numbers, and slashes")
    private String address;

    @NotBlank(message = "Phone number is required")
    @Pattern(regexp = "^(0\\d{9}|0\\d{2}[-./]\\d{3}[-./]\\d{4})$", message = "Phone number must start with 0 and have exactly 10 digits, optionally separated by a consistent separator")
    private String phone;

    @NotBlank(message = "Province is required")
    private String province;
    private String shippingInstruction;

    // Temp
    boolean isRush = false;
    int timeInMinute = 120;
}
