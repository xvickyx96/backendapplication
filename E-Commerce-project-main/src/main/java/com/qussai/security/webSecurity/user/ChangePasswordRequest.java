package com.qussai.security.webSecurity.user;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ChangePasswordRequest {

    // Represents a request to change the user's password.
    private String currentPassword;
    private String newPassword;
    private String confirmationPassword;
}
