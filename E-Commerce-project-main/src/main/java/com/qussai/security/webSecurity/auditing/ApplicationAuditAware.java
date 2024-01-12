package com.qussai.security.webSecurity.auditing;

import com.qussai.security.webSecurity.user.User;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

public class ApplicationAuditAware implements AuditorAware<Integer> {

    // This method is called to retrieve the current auditor, which is the user responsible for the current operation.
    @Override
    public Optional<Integer> getCurrentAuditor() {
        // Get the authentication information from the security context.
        Authentication authentication =
                SecurityContextHolder
                        .getContext()
                        .getAuthentication();
        // Check if there is no authentication or the user is not authenticated or it's an anonymous user.
        if (authentication == null ||
            !authentication.isAuthenticated() ||
                authentication instanceof AnonymousAuthenticationToken
        ) {
            // If any of the above conditions is true, return an empty Optional.
            return Optional.empty();
        }

        // Get the principal (authenticated user) from the authentication object.
        User userPrincipal = (User) authentication.getPrincipal();
        // Return the ID of the user as the current auditor.
        return Optional.ofNullable(userPrincipal.getId());
    }
}
