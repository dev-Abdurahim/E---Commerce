package org.example.ecommerce.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CartRequest {

    @NotNull(message = "Mahsulot tanlanishi shart")
    private Long productId;

    @NotNull(message = "Miqdor kiritilishi shart")
    @Positive(message = "Miqdor musbat son bo'lishi kerak")
    private Integer quantity;
}
