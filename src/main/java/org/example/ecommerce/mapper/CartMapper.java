package org.example.ecommerce.mapper;

import org.example.ecommerce.dto.response.CartItemResponse;
import org.example.ecommerce.dto.response.CartResponse;
import org.example.ecommerce.entity.Cart;
import org.example.ecommerce.entity.CartItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CartMapper {

    CartResponse toResponse(Cart cart);
    @Mapping(source = "product.name", target = "productName")
    @Mapping(source = "product.price",target = "price")
    CartItemResponse toItemResponse(CartItem cartItem);


}
