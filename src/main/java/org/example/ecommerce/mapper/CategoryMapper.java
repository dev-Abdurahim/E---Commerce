package org.example.ecommerce.mapper;

import org.example.ecommerce.dto.request.CategoryRequest;
import org.example.ecommerce.dto.response.CategoryResponse;
import org.example.ecommerce.entity.Category;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

    CategoryResponse toResponse(Category category);
    Category toEntity(CategoryRequest categoryRequest);
}
