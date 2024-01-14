package com.qussai.security.webSecurity.auth;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

  // Service responsible for handling authentication logic.
  private final AuthenticationService service;

  // Endpoint for admin registration.
  @PostMapping("/adminRegister")
  public ResponseEntity<AuthenticationResponse> register(
      @RequestBody RegisterRequest request
  ) {
    // Delegate the registration request to the AuthenticationService and return the response.
    return ResponseEntity.ok(service.adminRegister(request));
  }

  // Endpoint for user registration.
  @PostMapping("/userRegister")
  public ResponseEntity<AuthenticationResponse> userRegister(
          @RequestBody RegisterRequest request
  ) {
    // Delegate the user registration request to the AuthenticationService and return the response.
    return ResponseEntity.ok(service.userRegister(request));
  }

  // Endpoint for user authentication.
  @PostMapping("/authenticate")
  public ResponseEntity<AuthenticationResponse> authenticate(
      @RequestBody AuthenticationRequest request
  ) {
    // Delegate the authentication request to the AuthenticationService and return the response.
    return ResponseEntity.ok(service.authenticate(request));
  }

  // Endpoint for refreshing authentication tokens.
  @PostMapping("/refresh-token")
  public void refreshToken(
      HttpServletRequest request,
      HttpServletResponse response
  ) throws IOException {
    // Delegate the refresh token request to the AuthenticationService.
    service.refreshToken(request, response);
  }



}
