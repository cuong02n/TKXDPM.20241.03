package com.cuong02n.aimsbackend.config;

import com.cuong02n.aimsbackend.model.dto.response.OrderDto;
import com.cuong02n.aimsbackend.model.dto.response.OrderProductDto;
import com.cuong02n.aimsbackend.model.entity.Order;
import com.cuong02n.aimsbackend.model.entity.OrderProduct;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class MapperConfig {
    final ModelMapper modelMapper;

    @PostConstruct
    public void addOrderMapper() {
        modelMapper.getConfiguration().setFieldAccessLevel(org.modelmapper.config.Configuration.AccessLevel.PRIVATE);
//        modelMapper.typeMap(Order.class, OrderDto.class).addMappings(map -> {
//            map.map(Order::getOrderProducts, OrderDto::setOrderProductDtos); // Ánh xạ danh sách
//        });
//        modelMapper.typeMap(OrderProduct.class, OrderProductDto.class);
    }
}
