package org.example.ecommerce.service.temp;

import org.example.ecommerce.dto.request.CreateOrderRequest;
import org.example.ecommerce.dto.response.OrderResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface OrderService {

    OrderResponse checkout(Long userId, CreateOrderRequest request);

    Page<OrderResponse> getMyOrders(Long userId, int page, int size);

    OrderResponse getMyOrderById(Long userId,Long orderId);


}
