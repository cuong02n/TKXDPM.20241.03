package com.cuong02n.aimsbackend.model.dto.response;

import com.cuong02n.aimsbackend.model.entity.BaseEntity;
import com.cuong02n.aimsbackend.model.entity.FavoriteProductUser;
import com.cuong02n.aimsbackend.model.entity.Product;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.sql.Timestamp;

@Data
public class FavoriteProductUserDto {
    private Product product;
    private FavoriteProductUser.WishListKey key;
    private Timestamp createdTime;
    private Timestamp updatedTime;
}
