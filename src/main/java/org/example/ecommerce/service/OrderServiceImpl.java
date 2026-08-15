package org.example.ecommerce.service;

import lombok.RequiredArgsConstructor;
import org.example.ecommerce.dto.request.CreateOrderRequest;
import org.example.ecommerce.dto.response.OrderResponse;
import org.example.ecommerce.entity.*;
import org.example.ecommerce.enums.ErrorCode;
import org.example.ecommerce.enums.OrderStatus;
import org.example.ecommerce.exception.ApiException;
import org.example.ecommerce.mapper.OrderMapper;
import org.example.ecommerce.repository.CartRepository;
import org.example.ecommerce.repository.OrderRepository;
import org.example.ecommerce.repository.UserRepository;
import org.example.ecommerce.service.temp.OrderService;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final CartRepository cartRepository;
    private final UserRepository userRepository;
    private final OrderMapper orderMapper;

    @Override
    @Transactional
    public OrderResponse checkout(Long userId, CreateOrderRequest request) {

       Cart cart = cartRepository.findByUserId(userId)
                .orElseThrow(() -> new ApiException(ErrorCode.CART_NOT_FOUND));

       if(cart.getItems().isEmpty()){
           throw new ApiException(ErrorCode.EMPTY_CART);
       }

       User user = userRepository.findById(userId)
               .orElseThrow(() -> new ApiException(ErrorCode.USER_NOT_FOUND));

        Order order = new Order();
        order.setUser(user);
        order.setStatus(OrderStatus.PENDING);
        order.setDeliveryAddress(request.getDeliveryAddress());

        List<OrderItem> orderItems = new ArrayList<>();
        BigDecimal totalPrice = BigDecimal.ZERO;

        for (CartItem cartItem : cart.getItems()){
            Product product = cartItem.getProduct();

            if(product.getStockQuantity() < cartItem.getQuantity()){
                throw new ApiException(ErrorCode.INSUFFICIENT_STOCK,
                        "Omborda yetarli mahsulot yo'q: " + product.getName());
            }

            product.setStockQuantity(product.getStockQuantity() - cartItem.getQuantity());

            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);
            orderItem.setProduct(product);
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setPriceAtOrderTime(product.getPrice());

            orderItems.add(orderItem);

            totalPrice = totalPrice.add(
                    product.getPrice().multiply(BigDecimal.valueOf(cartItem.getQuantity()))
            );

        }

        order.setItems(orderItems);
        order.setTotalPrice(totalPrice);

        Order save = orderRepository.save(order);

        cart.getItems().clear();

        return orderMapper.toResponse(save);

    }

    @Override
    @Transactional(readOnly = true)
    public Page<OrderResponse> getMyOrders(Long userId, int page, int size) {
        Pageable pageable = PageRequest.of(page,size);
        return orderRepository.findByUserId(userId,pageable)
                .map(orderMapper::toResponse);
    }

    @Override
    public OrderResponse getMyOrderById(Long userId, Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ApiException(ErrorCode.ORDER_NOT_FOUND));
        if(!order.getUser().getId().equals(userId)){
            throw new ApiException(ErrorCode.ORDER_NOT_FOUND);
        }
        return orderMapper.toResponse(order);
    }
}
