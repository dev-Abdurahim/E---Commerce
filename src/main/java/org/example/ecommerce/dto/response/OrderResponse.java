package org.example.ecommerce.dto.response;

import lombok.*;
import org.example.ecommerce.enums.OrderStatus;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderResponse {

    private Long id;
    private List<OrderItemResponse> items;
    private BigDecimal totalPrice;
    private OrderStatus status;
    private String deliveryAddress;
}
