package org.example.ecommerce.service.temp;

import org.example.ecommerce.dto.request.CategoryRequest;
import org.example.ecommerce.dto.response.CategoryResponse;

import java.util.List;

public interface CategoryService {

    CategoryResponse create(CategoryRequest request);

    List<CategoryResponse> getAll();

    CategoryResponse getById(Long id);

    CategoryResponse update(Long id, CategoryRequest request);

    void delete(Long id);























}
