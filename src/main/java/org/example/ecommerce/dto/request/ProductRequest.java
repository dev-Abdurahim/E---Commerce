package org.example.ecommerce.dto.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductRequest {

    @NotBlank(message = "Mahsulot nomi majburiy")
    private String name;

    private String description;

    @NotNull(message = "Narx kiritilishi shart")
    @DecimalMin(value = "0.0", inclusive = false, message = "Narx 0 dan katta bo'lishi kerak")
    private BigDecimal price;

    @NotNull(message = "Miqdor kiritilishi shart")
    @PositiveOrZero(message = "Miqdor manfiy bo'lishi mumkin emas")
    private Integer stockQuantity;

    @NotNull(message = "Kategoriya tanlanishi shart")
    private Long categoryId;

    private String imageUrl;
}
