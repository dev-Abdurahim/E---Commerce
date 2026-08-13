package org.example.ecommerce.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CategoryRequest {

    @NotBlank(message = "Kategoriya nomi bo'sh bo'lishi mumkin emas")
    @Size(max = 100, message = "Kategoriya nomi 100 belgidan oshmasligi kerak")
    private String name;

    @Size(max = 255, message = "Tavsif 255 belgidan oshmasligi kerak")
    private String description;

}
