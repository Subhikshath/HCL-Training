package CanteenBooking.com.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter
        extends OncePerRequestFilter {


    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;


    public JwtAuthenticationFilter(
            JwtService jwtService,
            UserDetailsService userDetailsService
    ) {
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
    }


    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {


        /*
         * Get Authorization header.
         *
         * Expected format:
         *
         * Authorization: Bearer <JWT>
         */
        String authorizationHeader =
                request.getHeader("Authorization");


        /*
         * If there is no Authorization header,
         * continue the request.
         *
         * This is important for public endpoints
         * such as /api/auth/login.
         */
        if (authorizationHeader == null
                || !authorizationHeader.startsWith("Bearer ")) {

            filterChain.doFilter(request, response);
            return;
        }


        /*
         * Remove "Bearer " from the token.
         *
         * Example:
         *
         * Bearer abc123
         *
         * becomes:
         *
         * abc123
         */
        String token =
                authorizationHeader.substring(7);


        /*
         * Extract email from JWT.
         */
        String email;

        try {

            email = jwtService.extractEmail(token);

        } catch (Exception e) {

            /*
             * Invalid JWT.
             *
             * Continue without authentication.
             */
            filterChain.doFilter(request, response);
            return;
        }


        /*
         * Check whether the current request
         * is already authenticated.
         */
        if (email != null
                && SecurityContextHolder
                .getContext()
                .getAuthentication() == null) {


            UserDetails userDetails;

            try {

                /*
                 * Load user from database using email.
                 */
                userDetails =
                        userDetailsService
                                .loadUserByUsername(email);

            } catch (Exception e) {

                /*
                 * User does not exist.
                 */
                filterChain.doFilter(request, response);
                return;
            }


            /*
             * Validate JWT.
             *
             * Checks:
             * 1. Email matches
             * 2. Token is not expired
             * 3. Token signature is valid
             */
            if (jwtService.isTokenValid(
                    token,
                    userDetails.getUsername()
            )) {


                /*
                 * Create authenticated user.
                 */
                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(
                                userDetails,
                                null,
                                userDetails.getAuthorities()
                        );


                /*
                 * Add request details.
                 */
                authentication.setDetails(
                        new WebAuthenticationDetailsSource()
                                .buildDetails(request)
                );


                /*
                 * Store authentication in Spring Security context.
                 */
                SecurityContextHolder
                        .getContext()
                        .setAuthentication(authentication);
            }
        }


        /*
         * Continue the request.
         */
        filterChain.doFilter(request, response);
    }
}