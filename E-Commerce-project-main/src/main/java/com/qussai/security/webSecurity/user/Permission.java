package com.qussai.security.webSecurity.user;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

// Enum representing different permissions for admins and users.
@RequiredArgsConstructor
public enum Permission {

    // Permissions for admin operations.
    ADMIN_READ("admin:read"),
    ADMIN_UPDATE("admin:update"),
    ADMIN_CREATE("admin:create"),
    ADMIN_DELETE("admin:delete"),

    // Permissions for user operations.
    USER_READ("user:read"),
    USER_UPDATE("user:update"),
    USER_CREATE("user:create"),
    USER_DELETE("user:delete")

    ;

    // The actual permission string.
    @Getter
    private final String permission;
}
