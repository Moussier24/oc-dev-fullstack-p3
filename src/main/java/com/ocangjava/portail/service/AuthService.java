package com.ocangjava.portail.service;

import com.ocangjava.portail.dto.AuthRequest;
import com.ocangjava.portail.dto.AuthResponse;
import com.ocangjava.portail.dto.RegisterRequest;
import com.ocangjava.portail.model.User;
import com.ocangjava.portail.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthResponse register(RegisterRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email déjà utilisé");
        }

        User user = new User();
        user.setEmail(request.getEmail());
        user.setName(request.getName());
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        userRepository.save(user);
        return AuthResponse.builder()
                .token(jwtService.generateToken(user))
                .build();
    }

    public AuthResponse authenticate(AuthRequest request) {
        // Implémentation de l'authentification
        return AuthResponse.builder()
                .token(jwtService.generateToken(userRepository.findByEmail(request.getEmail())
                        .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"))))
                .build();
    }
}