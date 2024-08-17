package com.example.rent.management.system.easerent.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;


@Configuration
@EnableWebSecurity
public class SecurityConfig {

	@Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
	
	//TODO: Learn more about CSRF and Spring Security
	@Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf
                .ignoringRequestMatchers("/api/**") // Disable CSRF for API endpoints
            )
            .authorizeRequests(authorizeRequests ->
                authorizeRequests
                    .requestMatchers("/api/auth/signup").permitAll() // Allow access to the signup endpoint without authentication
                    .anyRequest().authenticated() // All other requests require authentication
            )
            .logout(logout ->
                logout
                    .permitAll() // Allow everyone to log out
            );

        return http.build();
    }
}

