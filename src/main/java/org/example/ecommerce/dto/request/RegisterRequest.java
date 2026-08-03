package org.example.ecommerce.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegisterRequest {

    @NotBlank(message = "Username bo'sh bo'lishi mumkin emas")
    @Size(min = 3, max = 50, message = "Username 3-50 belgidan iborat bo'lishi kerak")
    private String username;

    @NotBlank(message = "Parol bo'sh bo'lishi mumkin emas")
    @Size(min = 6, message = "Parol kamida 6 belgidan iborat bo'lishi kerak")
    private String password;

    @NotBlank(message = "To'liq ism bo'sh bo'lishi mumkin emas")
    private String fullName;
}
