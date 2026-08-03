package org.example.ecommerce.mapper;

import org.example.ecommerce.dto.response.ProductResponse;
import org.example.ecommerce.entity.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    @Mapping(source = "category.name", target = "categoryName")
    ProductResponse toResponse(Product product);
}
