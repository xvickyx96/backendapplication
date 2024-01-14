package com.qussai.security.webSecurity.user;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.qussai.security.webSecurity.user.Permission.ADMIN_CREATE;
import static com.qussai.security.webSecurity.user.Permission.ADMIN_DELETE;
import static com.qussai.security.webSecurity.user.Permission.ADMIN_READ;
import static com.qussai.security.webSecurity.user.Permission.ADMIN_UPDATE;
import static com.qussai.security.webSecurity.user.Permission.USER_CREATE;
import static com.qussai.security.webSecurity.user.Permission.USER_DELETE;
import static com.qussai.security.webSecurity.user.Permission.USER_READ;
import static com.qussai.security.webSecurity.user.Permission.USER_UPDATE;

@RequiredArgsConstructor
public enum Role {

  // Admin role with full permissions.
  ADMIN(
          Set.of(
                  ADMIN_READ,
                  ADMIN_UPDATE,
                  ADMIN_DELETE,
                  ADMIN_CREATE,
                  USER_READ,
                  USER_UPDATE,
                  USER_DELETE,
                  USER_CREATE
          )
  ),

  // User role with limited permissions.
  USER(
          Set.of(
                  USER_READ,
                  USER_UPDATE,
                  USER_DELETE,
                  USER_CREATE
          )
  );

  // Set of permissions associated with the role.
  @Getter
  private final Set<Permission> permissions;

  // Method to convert permissions to authorities for Spring Security.
  public List<SimpleGrantedAuthority> getAuthorities() {
    var authorities = getPermissions()
            .stream()
            .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
            .collect(Collectors.toList());

    // Add a role authority based on the role name.
    authorities.add(new SimpleGrantedAuthority("ROLE_" + this.name()));

    return authorities;
  }
}
