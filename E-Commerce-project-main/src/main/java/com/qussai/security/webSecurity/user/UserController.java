package com.qussai.security.webSecurity.user;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    // Service responsible for user-related business logic.
    private final UserService service;

    // Endpoint for changing the password of the currently authenticated user.
    @PatchMapping
    public ResponseEntity<?> changePassword(
          @RequestBody ChangePasswordRequest request,
          Principal connectedUser
    ) {
        // Delegate the password change request to the service.
        service.changePassword(request, connectedUser);

        // Return a successful response.
        return ResponseEntity.ok().build();
    }


}
