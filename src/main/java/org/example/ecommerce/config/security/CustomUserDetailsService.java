package org.example.ecommerce.config.security;

import lombok.RequiredArgsConstructor;
import org.example.ecommerce.entity.User;
import org.example.ecommerce.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Foydalanuvchi topilmadi : " + username));
        return new UserPrincipal(user);
    }
}

// bu kodimiz login paytida ishlatiladi
