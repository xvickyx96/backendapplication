package com.qussai.security.webSecurity.auth;

import com.qussai.security.webSecurity.user.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

// This class represents a request object for user registration.
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {

  private String firstname;
  private String lastname;
  private String email;
  private String password;
  // The role assigned to the user being registered (e.g., ADMIN or USER).
  private Role role;
}
