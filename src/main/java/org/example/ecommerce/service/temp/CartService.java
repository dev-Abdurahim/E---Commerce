package org.example.ecommerce.service.temp;

import org.example.ecommerce.dto.request.CartRequest;
import org.example.ecommerce.dto.request.CategoryRequest;
import org.example.ecommerce.dto.request.UpdateCartItemRequest;
import org.example.ecommerce.dto.response.CartResponse;

public interface CartService {

    CartResponse getMyCart(Long userId);

    CartResponse addItem(Long userId, CartRequest request);

    CartResponse updateItem(Long userId, Long cartItemId, UpdateCartItemRequest request);

    CartResponse removeItem(Long userId, Long cartItemId);


}
