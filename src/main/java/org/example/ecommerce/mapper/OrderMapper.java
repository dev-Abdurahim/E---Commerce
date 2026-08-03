package org.example.ecommerce.mapper;

import org.example.ecommerce.dto.response.OrderItemResponse;
import org.example.ecommerce.dto.response.OrderResponse;
import org.example.ecommerce.entity.Order;
import org.example.ecommerce.entity.OrderItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface OrderMapper {

    OrderResponse toResponse(Order order);

    @Mapping(source = "product.name", target = "productName")
    OrderItemResponse toItemResponse(OrderItem orderItem);

}
