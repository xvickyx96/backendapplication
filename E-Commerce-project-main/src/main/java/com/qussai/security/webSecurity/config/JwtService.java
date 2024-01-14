package com.qussai.security.webSecurity.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class JwtService {

  // Secret key for signing and verifying JWTs, loaded from application properties.
  @Value("${application.security.jwt.secret-key}")
  private String secretKey;

  // Expiration time for JWTs, loaded from application properties.
  @Value("${application.security.jwt.expiration}")
  private long jwtExpiration;

  // Expiration time for refresh tokens, loaded from application properties.
  @Value("${application.security.jwt.refresh-token.expiration}")
  private long refreshExpiration;

  // Extracts the username from a JWT.
  public String extractUsername(String token) {
    return extractClaim(token, Claims::getSubject);
  }

  // Extracts a claim from a JWT using a provided claims resolver.
  public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
    final Claims claims = extractAllClaims(token);
    return claimsResolver.apply(claims);
  }

  // Generates a JWT for the given user details and optional extra claims.
  public String generateToken(UserDetails userDetails) {
    return generateToken(new HashMap<>(), userDetails);
  }

  // Generates a JWT with extra claims for the given user details and optional extra claims.
  public String generateToken(
      Map<String, Object> extraClaims,
      UserDetails userDetails
  ) {
    return buildToken(extraClaims, userDetails, jwtExpiration);
  }

  // Generates a refresh token for the given user details.
  public String generateRefreshToken(
      UserDetails userDetails
  ) {
    return buildToken(new HashMap<>(), userDetails, refreshExpiration);
  }

  // Builds a JWT with the provided claims and expiration time.
  private String buildToken(
          Map<String, Object> extraClaims,
          UserDetails userDetails,
          long expiration
  ) {
    return Jwts
            .builder()
            .setClaims(extraClaims)
            .setSubject(userDetails.getUsername())
            .setIssuedAt(new Date(System.currentTimeMillis()))
            .setExpiration(new Date(System.currentTimeMillis() + expiration))
            .signWith(getSignInKey(), SignatureAlgorithm.HS256)
            .compact();
  }

  // Checks if a JWT is valid for the given user details.
  public boolean isTokenValid(String token, UserDetails userDetails) {
    final String username = extractUsername(token);
    return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
  }

  // Checks if a JWT is expired.
  private boolean isTokenExpired(String token) {
    return extractExpiration(token).before(new Date());
  }

  // Extracts the expiration date from a JWT.
  private Date extractExpiration(String token) {
    return extractClaim(token, Claims::getExpiration);
  }

  // Extracts all claims from a JWT.
  private Claims extractAllClaims(String token) {
    return Jwts
        .parserBuilder()
        .setSigningKey(getSignInKey())
        .build()
        .parseClaimsJws(token)
        .getBody();
  }

  // Gets the signing key for JWTs from the secret key.
  private Key getSignInKey() {
    byte[] keyBytes = Decoders.BASE64.decode(secretKey);
    return Keys.hmacShaKeyFor(keyBytes);
  }
}
