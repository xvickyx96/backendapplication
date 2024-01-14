package com.qussai.security.webSecurity.config;

import com.qussai.security.webSecurity.token.TokenRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

  // Service for handling JSON Web Tokens (JWT).
  private final JwtService jwtService;
  // Service for loading user details.
  private final UserDetailsService userDetailsService;
  // Repository for accessing token data.
  private final TokenRepository tokenRepository;

  // This method is called for each incoming HTTP request to perform JWT authentication.
  @Override
  protected void doFilterInternal(
      @NonNull HttpServletRequest request,
      @NonNull HttpServletResponse response,
      @NonNull FilterChain filterChain
  ) throws ServletException, IOException {
    // Skip authentication for requests to the authentication endpoint.
    if (request.getServletPath().contains("/api/v1/auth")) {
      filterChain.doFilter(request, response);
      return;
    }
    // Extract the JWT token from the Authorization header.
    final String authHeader = request.getHeader("Authorization");
    final String jwt;
    final String userEmail;

    // If there is no Authorization header or it doesn't start with "Bearer ", continue to the next filter.
    if (authHeader == null ||!authHeader.startsWith("Bearer ")) {
      filterChain.doFilter(request, response);
      return;
    }
    // Extract the JWT token and the user email from the Authorization header.
    jwt = authHeader.substring(7);
    userEmail = jwtService.extractUsername(jwt);

    // If the user email is not null and there is no existing authentication in the SecurityContextHolder.
    if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {

      // Load user details from the UserDetailsService.
      UserDetails userDetails = this.userDetailsService.loadUserByUsername(userEmail);

      // Check if the token is valid and not expired or revoked.
      var isTokenValid = tokenRepository.findByToken(jwt)
          .map(t -> !t.isExpired() && !t.isRevoked())
          .orElse(false);

      // If the token is valid, create an authentication token and set it in the SecurityContextHolder.
      if (jwtService.isTokenValid(jwt, userDetails) && isTokenValid) {
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
            userDetails,
            null,
            userDetails.getAuthorities()
        );
        authToken.setDetails(
            new WebAuthenticationDetailsSource().buildDetails(request)
        );
        SecurityContextHolder.getContext().setAuthentication(authToken);
      }
    }
    // Continue with the filter chain.
    filterChain.doFilter(request, response);
  }
}
