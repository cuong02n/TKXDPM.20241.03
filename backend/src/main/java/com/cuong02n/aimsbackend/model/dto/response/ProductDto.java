package com.cuong02n.aimsbackend.model.dto.response;

import com.cuong02n.aimsbackend.model.entity.Product;
import lombok.Data;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
@Data
public class ProductDto{
    private long id;
    private String name;
    private String description;
    private int available;
    private int price;
    private double weight;
    private HashMap<String, String> additionalData;
    private Product.ProductCategory category;
    private List<String> mediaUrls;
    private Timestamp createdTime;
    private Timestamp updatedTime;
}
