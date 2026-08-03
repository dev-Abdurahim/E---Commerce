package org.example.ecommerce.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoginRequest {

    @NotBlank(message = "Username majburiy")
    private String username;

    @NotBlank(message = "Password majburiy")
    private String password;
}
