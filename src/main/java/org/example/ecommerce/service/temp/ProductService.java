package org.example.ecommerce.service.temp;

import org.example.ecommerce.dto.request.ProductRequest;
import org.example.ecommerce.dto.response.ProductResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductService {

    ProductResponse create(ProductRequest request);

    Page<ProductResponse> getAll(Long categoryId, String search, int page, int size);

    ProductResponse getById(Long id);

    ProductResponse update(Long id, ProductRequest request);

    void delete(Long id);

}
