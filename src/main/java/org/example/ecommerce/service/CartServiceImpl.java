package org.example.ecommerce.service;

import lombok.RequiredArgsConstructor;
import org.example.ecommerce.dto.request.CartRequest;
import org.example.ecommerce.dto.request.UpdateCartItemRequest;
import org.example.ecommerce.dto.response.CartResponse;
import org.example.ecommerce.entity.Cart;
import org.example.ecommerce.entity.CartItem;
import org.example.ecommerce.entity.Product;
import org.example.ecommerce.entity.User;
import org.example.ecommerce.enums.ErrorCode;
import org.example.ecommerce.exception.ApiException;
import org.example.ecommerce.mapper.CartMapper;
import org.example.ecommerce.repository.CartItemRepository;
import org.example.ecommerce.repository.CartRepository;
import org.example.ecommerce.repository.ProductRepository;
import org.example.ecommerce.repository.UserRepository;
import org.example.ecommerce.service.temp.CartService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final ProductRepository productRepository;
    private final CartMapper cartMapper;
    private final UserRepository userRepository;

    @Override
    @Transactional
    public CartResponse getMyCart(Long userId) {
        return cartMapper.toResponse(findOrCreateCart(userId));
    }

    @Override
    @Transactional
    public CartResponse addItem(Long userId, CartRequest request) {
        Cart cart = findOrCreateCart(userId);

        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new ApiException(ErrorCode.PRODUCT_NOT_FOUND));

        if (product.getStockQuantity() < request.getQuantity()) {
            throw new ApiException(ErrorCode.INSUFFICIENT_STOCK);
        }
        var existingItem = cartItemRepository.findByCartIdAndProductId(cart.getId(), product.getId());

        if (existingItem.isPresent()) {
            CartItem item = existingItem.get();
            item.setQuantity(item.getQuantity() + request.getQuantity());
        } else {
            CartItem newItem = new CartItem();
            newItem.setCart(cart);
            newItem.setProduct(product);
            newItem.setQuantity(request.getQuantity());

            CartItem savedItem  = cartItemRepository.save(newItem);

            cart.getItems().add(savedItem);

        }
        return cartMapper.toResponse(cart);
    }

    @Override
    @Transactional
    public CartResponse updateItem(Long userId, Long cartItemId, UpdateCartItemRequest request) {
        Cart cart = findOrCreateCart(userId);
        CartItem item = findItemBelongingToCart(cart, cartItemId);

        if(item.getProduct().getStockQuantity() < request.getQuantity()){
            throw new ApiException(ErrorCode.INSUFFICIENT_STOCK);
        }
        item.setQuantity(request.getQuantity());
        return cartMapper.toResponse(cart);

    }

    @Override
    @Transactional
    public CartResponse removeItem(Long userId, Long cartItemId) {
        Cart cart = findOrCreateCart(userId);
        CartItem item = findItemBelongingToCart(cart, cartItemId);

        cart.getItems().remove(item);
        return cartMapper.toResponse(cart);
    }

    private Cart findOrCreateCart(Long userId) {
        return cartRepository.findByUserId(userId)
                .orElseGet(() -> {
                    User user = userRepository.findById(userId)
                            .orElseThrow(() -> new ApiException(ErrorCode.USER_NOT_FOUND));
                    Cart newCart = new Cart();
                    newCart.setUser(user);
                    return cartRepository.save(newCart);
                });
    }
    private CartItem findItemBelongingToCart(Cart cart, Long cartItemId){
        return cart.getItems().stream()
                .filter(item -> item.getId().equals(cartItemId))
                .findFirst()
                .orElseThrow(() -> new ApiException(ErrorCode.CART_ITEM_NOT_FOUND));
    }
}
