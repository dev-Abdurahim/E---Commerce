package org.example.ecommerce.service;

import lombok.RequiredArgsConstructor;
import org.example.ecommerce.config.jwt.JwtService;
import org.example.ecommerce.config.security.CustomUserDetailsService;
import org.example.ecommerce.dto.request.LoginRequest;
import org.example.ecommerce.dto.request.RegisterRequest;
import org.example.ecommerce.dto.response.AuthResponse;
import org.example.ecommerce.entity.User;
import org.example.ecommerce.enums.ErrorCode;
import org.example.ecommerce.enums.UserRole;
import org.example.ecommerce.exception.ApiException;
import org.example.ecommerce.repository.UserRepository;
import org.example.ecommerce.service.temp.UserService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final CustomUserDetailsService userDetailsService;

    @Override
    @Transactional
    public AuthResponse register(RegisterRequest request) {
        if(userRepository.existsByUsername(request.getUsername())){
          throw new ApiException(ErrorCode.USERNAME_ALREADY_EXISTS);

        }
        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setFullName(request.getFullName());
        user.setRoles(Set.of(UserRole.USER));

        User savedUser = userRepository.save(user);
        UserDetails userDetails = userDetailsService.loadUserByUsername(savedUser.getUsername());
        return buildAuthResponse(userDetails);
    }


    @Override
    public AuthResponse login(LoginRequest request) {
       try {
           authenticationManager.authenticate(
                   new UsernamePasswordAuthenticationToken(request.getUsername(),request.getPassword())
           );
       } catch (BadCredentialsException ex){
           throw new ApiException(ErrorCode.INVALID_CREDENTIALS);
       }
        UserDetails userDetails = userDetailsService.loadUserByUsername(request.getUsername());
        return buildAuthResponse(userDetails);
    }

    private AuthResponse buildAuthResponse(UserDetails userDetails) {
        return AuthResponse.builder()
                .accessToken(jwtService.generateAccessToken(userDetails))
                .refreshToken(jwtService.generateRefreshToken(userDetails))
                .username(userDetails.getUsername())
                .build();
    }
}
