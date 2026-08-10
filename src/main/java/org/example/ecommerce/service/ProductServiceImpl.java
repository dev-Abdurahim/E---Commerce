package org.example.ecommerce.service;

import lombok.RequiredArgsConstructor;
import org.example.ecommerce.dto.request.ProductRequest;
import org.example.ecommerce.dto.response.ProductResponse;
import org.example.ecommerce.entity.Category;
import org.example.ecommerce.entity.Product;
import org.example.ecommerce.enums.ErrorCode;
import org.example.ecommerce.exception.ApiException;
import org.example.ecommerce.mapper.ProductMapper;
import org.example.ecommerce.repository.CategoryRepository;
import org.example.ecommerce.repository.ProductRepository;
import org.example.ecommerce.service.temp.ProductService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;


@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ProductMapper productMapper;

    @Override
    @Transactional
    public ProductResponse create(ProductRequest request) {
       Category category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new ApiException(ErrorCode.CATEGORY_NOT_FOUND));
        Product product = new Product();
        product.setName(request.getName());
        product.setDescription(request.getDescription());
        product.setPrice(request.getPrice());
        product.setStockQuantity(request.getStockQuantity());
        product.setImageUrl(request.getImageUrl());
        product.setCategory(category);
        Product saved = productRepository.save(product);
        return productMapper.toResponse(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ProductResponse> getAll(Long categoryId, String search, Pageable pageable) {
        Page<Product> products;
        if(categoryId != null){
            products = productRepository.findByCategoryId(categoryId,pageable);
        } else if (StringUtils.hasText(search)) {
            products = productRepository.findByNameContainingIgnoreCase(search, pageable);
            
        }
        else {
           products = productRepository.findAll(pageable);
        }
        return products.map(productMapper::toResponse);

    }

    @Override
    @Transactional(readOnly = true)
    public ProductResponse getById(Long id) {
        Product product = findEntityById(id);
        return productMapper.toResponse(product);
    }

    @Override
    public ProductResponse update(Long id, ProductRequest request) {
        Product product = findEntityById(id);

        if(!product.getCategory().getId().equals(request.getCategoryId())){
            Category category = categoryRepository.findById(request.getCategoryId())
                    .orElseThrow(() -> new ApiException(ErrorCode.CATEGORY_NOT_FOUND));
            product.setCategory(category);
        }
        product.setName(request.getName());
        product.setDescription(request.getDescription());
        product.setPrice(request.getPrice());
        product.setStockQuantity(request.getStockQuantity());
        product.setImageUrl(request.getImageUrl());
        return productMapper.toResponse(product);
    }

    @Override
    public void delete(Long id) {
        Product product = findEntityById(id);
        product.delete();

    }
    private Product findEntityById(Long id){
        return productRepository.findById(id)
                .orElseThrow(() -> new ApiException(ErrorCode.PRODUCT_NOT_FOUND));
    }
}
