package org.example.ecommerce;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.ecommerce.entity.User;
import org.example.ecommerce.enums.UserRole;
import org.example.ecommerce.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
@RequiredArgsConstructor
@Slf4j
public class DataInitializer {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @PostConstruct
    public void initAdmin(){
        if(userRepository.existsByUsername("admin")){
            return;
        }
        User admin = new User();
        admin.setUsername("admin");
        admin.setPassword(passwordEncoder.encode("Admin123"));
        admin.setFullName("System Admin");
        admin.setRoles(Set.of(UserRole.ADMIN));
        userRepository.save(admin);
        log.info("ADMIN USER CREATED");
    }
}
