package com.qussai.security.webSecurity.auth;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationResponse {

  // JSON property name for the access token in the response.
  @JsonProperty("access_token")
  private String accessToken;
  // JSON property name for the refresh token in the response.
  @JsonProperty("refresh_token")
  private String refreshToken;
}
