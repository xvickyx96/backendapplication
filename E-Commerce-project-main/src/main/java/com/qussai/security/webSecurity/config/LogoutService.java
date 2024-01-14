package com.qussai.security.webSecurity.config;

import com.qussai.security.webSecurity.token.TokenRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Service;


// Service class for handling user logout.
@Service
@RequiredArgsConstructor
public class LogoutService implements LogoutHandler {

  // Repository for accessing token data.
  private final TokenRepository tokenRepository;


  // This method is called when a user logs out, and it handles the revocation of the user's token.
  @Override
  public void logout(
      HttpServletRequest request,
      HttpServletResponse response,
      Authentication authentication
  ) {
    // Extract the JWT token from the Authorization header.
    final String authHeader = request.getHeader("Authorization");
    final String jwt;
    // If there is no Authorization header or it doesn't start with "Bearer ", return.
    if (authHeader == null ||!authHeader.startsWith("Bearer ")) {
      return;
    }

    // Extract the JWT token from the Authorization header.
    jwt = authHeader.substring(7);

    // Find the stored token associated with the JWT.
    var storedToken = tokenRepository.findByToken(jwt)
        .orElse(null);

    // If the stored token is not null, mark it as expired and revoked, and save the changes.
    if (storedToken != null) {
      storedToken.setExpired(true);
      storedToken.setRevoked(true);
      tokenRepository.save(storedToken);

      // Clear the security context to signify the user has been logged out.
      SecurityContextHolder.clearContext();
    }
  }
}
