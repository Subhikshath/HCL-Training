package CanteenBooking.com.auth.controller;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestAuthController {

    @GetMapping("/api/test/auth")
    public String testAuthentication(
            Authentication authentication
    ) {

        return "Authenticated user: "
                + authentication.getName()
                + " | Role: "
                + authentication.getAuthorities();
    }
}