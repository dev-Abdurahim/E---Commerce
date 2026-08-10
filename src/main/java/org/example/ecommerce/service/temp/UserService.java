package org.example.ecommerce.service.temp;

import org.example.ecommerce.dto.request.LoginRequest;
import org.example.ecommerce.dto.request.RegisterRequest;
import org.example.ecommerce.dto.response.AuthResponse;

public interface UserService {

    AuthResponse register(RegisterRequest request);

    AuthResponse login(LoginRequest loginRequest);
}
