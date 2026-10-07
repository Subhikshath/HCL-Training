package CanteenBooking.com.auth.service;

import CanteenBooking.com.auth.dto.AuthResponse;
import CanteenBooking.com.auth.dto.LoginRequest;
import CanteenBooking.com.security.JwtService;
import CanteenBooking.com.user.entity.User;
import CanteenBooking.com.user.repository.UserRepository;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public AuthService(
            UserRepository userRepository,
            AuthenticationManager authenticationManager,
            JwtService jwtService
    ) {
        this.userRepository = userRepository;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }


    public AuthResponse login(LoginRequest request) {

        /*
         * 1. Authenticate email + password.
         *
         * If credentials are wrong,
         * Spring Security throws an exception.
         */
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );


        /*
         * 2. Get user from database.
         */
        User user = userRepository
                .findByEmail(request.getEmail())
                .orElseThrow(() ->
                        new RuntimeException(
                                "User not found"
                        )
                );


        /*
         * 3. Generate JWT.
         */
        String token = jwtService.generateToken(
                user.getEmail(),
                user.getRole().name()
        );


        /*
         * 4. Return user information + JWT.
         */
        return new AuthResponse(
                token,
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getRole().name()
        );
    }
}