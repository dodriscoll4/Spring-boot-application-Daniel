package com.example.lab5project2.security;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableMethodSecurity(securedEnabled = true)
public class SecurityConfig {

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests((auth) ->
                        auth.requestMatchers(HttpMethod.POST, "/graphql/**").permitAll()
                                .requestMatchers(HttpMethod.POST).hasAnyRole("ADMIN")
                                .requestMatchers(HttpMethod.PUT).hasAnyRole("ADMIN", "USER")
                                .requestMatchers(HttpMethod.PATCH).hasAnyRole("ADMIN", "USER")
                                .requestMatchers(PathRequest.toH2Console()).permitAll() // Allow H2 console access
                                .requestMatchers(HttpMethod.DELETE).hasRole("ADMIN") // Restrict DELETE requests to /api/** to ADMIN role
                                .requestMatchers(HttpMethod.GET).permitAll() // Allow all GET requests
                                .anyRequest().authenticated() // Allow all other requests
                )
                .headers(headers -> headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin)) // Only allows content in frames from the same origin (no embedded content) (for H2 console)
                .csrf(AbstractHttpConfigurer::disable)  // Disable CSRF protection (handle in production)
                .sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // Set session management to stateless
                .cors(Customizer.withDefaults()) // Enable CORS with default configuration
                .httpBasic(Customizer.withDefaults()) // Enable HTTP Basic authentication with default settings
                .formLogin(Customizer.withDefaults()); // Enable form-based login with default settings
        return http.build();
    }
}
