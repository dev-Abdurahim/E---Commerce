package org.example.ecommerce.service;

import lombok.RequiredArgsConstructor;
import org.example.ecommerce.dto.request.CategoryRequest;
import org.example.ecommerce.dto.response.CategoryResponse;
import org.example.ecommerce.entity.Category;
import org.example.ecommerce.enums.ErrorCode;
import org.example.ecommerce.exception.ApiException;
import org.example.ecommerce.mapper.CategoryMapper;
import org.example.ecommerce.repository.CategoryRepository;
import org.example.ecommerce.service.temp.CategoryService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    @Override
    @Transactional
    public CategoryResponse create(CategoryRequest request) {
        if(categoryRepository.existsByName(request.getName())){
            throw new ApiException(ErrorCode.CATEGORY_ALREADY_EXISTS);
        }
        Category category = categoryMapper.toEntity(request);
        Category save = categoryRepository.save(category);
        return categoryMapper.toResponse(save);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CategoryResponse> getAll() {
      return categoryRepository.findAll().stream()
              .map(categoryMapper::toResponse)
              .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public CategoryResponse getById(Long id) {
        Category category = findEntityById(id);
        return categoryMapper.toResponse(category);
    }

    @Override
    @Transactional
    public CategoryResponse update(Long id, CategoryRequest request) {
        Category category = findEntityById(id);
        category.setName(request.getName());
        category.setDescription(request.getDescription());
        return categoryMapper.toResponse(category);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Category category = findEntityById(id);
        category.delete();

    }

    private Category findEntityById(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new ApiException(ErrorCode.CATEGORY_NOT_FOUND));
    }
}
