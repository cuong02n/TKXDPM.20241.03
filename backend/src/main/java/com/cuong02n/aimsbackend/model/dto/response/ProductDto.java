package com.cuong02n.aimsbackend.model.dto.response;

import com.cuong02n.aimsbackend.model.entity.Product;
import lombok.Data;

import java.util.HashMap;
import java.util.List;
@Data
public class ProductDto {
    private long id;
    private String name;
    private String description;
    private int available;
    private int price;
    private HashMap<String, String> additionalData;
    private Product.ProductCategory category;
    private List<String> mediaUrls;
}
