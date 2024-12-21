package com.cuong02n.aimsbackend.config;

import com.cuong02n.aimsbackend.model.dto.response.OrderDto;
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
        modelMapper
                .typeMap(Order.class, OrderDto.class)
                .addMapping(Order::getOrderProducts,OrderDto::setOrderProductDtos);
        // TODO
    }
}
