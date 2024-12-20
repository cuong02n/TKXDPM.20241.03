package com.cuong02n.aimsbackend.model.dto.response;

import com.cuong02n.aimsbackend.model.entity.Product;
import com.cuong02n.aimsbackend.model.entity.ProductCart;
import lombok.Data;

@Data
public class ProductCartDto {
    private ProductCart.ProductCartKey key;
    private Product product;
    private int quantity;
}
