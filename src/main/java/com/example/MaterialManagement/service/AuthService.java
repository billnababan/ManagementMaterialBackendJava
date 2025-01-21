package com.example.MaterialManagement.service;

import com.example.MaterialManagement.dto.request.LoginRequest;
import com.example.MaterialManagement.dto.request.RegisterRequest;
import com.example.MaterialManagement.dto.response.JwtResponse;
import com.example.MaterialManagement.entity.User;
import com.example.MaterialManagement.exception.ResourceNotFoundException;  // Add this import
import com.example.MaterialManagement.repository.UserRepository;
import com.example.MaterialManagement.security.JwtUtils;
import com.example.MaterialManagement.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;
    private final AuthenticationManager authenticationManager;

    public void register(RegisterRequest request) {
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new RuntimeException("Username is already taken!");
        }

        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setDepartment(request.getDepartment());

        userRepository.save(user);
    }

    public JwtResponse authenticate(LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                loginRequest.getUsername(),
                loginRequest.getPassword()
            )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        String jwt = jwtUtils.generateJwtToken(userDetails);  // Changed to use UserDetails
        
        User user = userRepository.findById(userDetails.getId())
            .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        return JwtResponse.builder()
            .token(jwt)
            .id(user.getId())
            .username(user.getUsername())
            .department(user.getDepartment())
            .build();
    }
}