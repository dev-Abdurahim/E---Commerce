package org.example.ecommerce.dto.response;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemResponse {
    private Long id;
    private String productName;
    private Integer quantity;
    private BigDecimal priceAtOrderTime;
}
