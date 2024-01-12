package com.qussai.security.webSecurity.config;

import com.qussai.security.webSecurity.user.Role;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutHandler;

import static org.springframework.http.HttpMethod.*;
import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableMethodSecurity
public class SecurityConfiguration {

    // URL patterns to be allowed without authentication.
    private static final String[] WHITE_LIST_URL = {
            "/api/v1/auth/**",
            };

    // JWT authentication filter.
    private final JwtAuthenticationFilter jwtAuthFilter;

    // Custom authentication provider.
    private final AuthenticationProvider authenticationProvider;

    // Logout handler for handling user logout.
    private final LogoutHandler logoutHandler;


    // Configures the security filter chain.
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // Disable CSRF protection.
                .csrf(AbstractHttpConfigurer::disable)
                // Define authorization rules for different endpoints.
                .authorizeHttpRequests(req ->
                        req.requestMatchers(WHITE_LIST_URL)
                                .permitAll()
                                .requestMatchers(PATCH, "/api/v1/users/**").hasAnyRole(Role.ADMIN.name(), Role.USER.name())
                                .requestMatchers(POST, "/addnewproducts/**").hasAnyRole(Role.ADMIN.name())
                                .requestMatchers(PUT, "/updateproducts/**").hasAnyRole(Role.ADMIN.name())
                                .requestMatchers(DELETE, "/deleteproduct/**").hasAnyRole(Role.ADMIN.name())
                                .requestMatchers(GET, "/Cart/**").hasAnyRole(Role.ADMIN.name(), Role.USER.name())
                                .requestMatchers(POST, "/Cart/**").hasAnyRole(Role.ADMIN.name(), Role.USER.name())
                                .requestMatchers(PUT, "/Cart/**").hasAnyRole(Role.ADMIN.name(), Role.USER.name())
                                .requestMatchers(DELETE, "/Cart/**").hasAnyRole(Role.ADMIN.name(), Role.USER.name())
                                .anyRequest()
                                .authenticated()
                )
                // Configure session management to be stateless.
                .sessionManagement(session -> session.sessionCreationPolicy(STATELESS))
                // Set the custom authentication provider.
                .authenticationProvider(authenticationProvider)
                // Add the JWT authentication filter before the UsernamePasswordAuthenticationFilter.
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                // Configure logout handling.
                .logout(logout ->
                        logout.logoutUrl("/api/v1/auth/logout")
                                .addLogoutHandler(logoutHandler)
                                .logoutSuccessHandler((request, response, authentication) -> SecurityContextHolder.clearContext())
                )
                // Configure exception handling for access denied scenarios.
                .exceptionHandling(exceptionHandling ->
                        exceptionHandling.accessDeniedHandler((request, response, accessDeniedException) ->
                                response.sendError(HttpServletResponse.SC_FORBIDDEN, "Access Denied")))
        ;

        // Build and return the configured security filter chain.
        return http.build();
    }
}
