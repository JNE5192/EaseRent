package com.example.rent.management.system.easerent.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.example.rent.management.system.easerent.service.CustomUserDetailsService;

import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.server.session.HeaderWebSessionIdResolver;


@Configuration
@EnableWebSecurity
public class SecurityConfig {

	@Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
	
	 @Bean
	    public HeaderWebSessionIdResolver httpSessionIdResolver() {
	        return new HeaderWebSessionIdResolver(); // Use Cookie-based session ID resolver
	    }
	
	//TODO: Learn more about CSRF and Spring Security
	@Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf
                .disable() // Disable CSRF for API endpoints
            )
            .authorizeRequests(authorizeRequests ->
                authorizeRequests
                    .requestMatchers("/api/auth/**").permitAll() // Allow access to the signup endpoint without authentication
                    .requestMatchers("/tenant/**").permitAll()
                    .requestMatchers("/property/**").permitAll()
                    .requestMatchers("/error/**").permitAll()
                    .anyRequest().authenticated() // All other requests require authentication
            )
            .logout(logout ->
                logout
                	.invalidateHttpSession(true)
                    .permitAll() // Allow everyone to log out
            )
            .sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED);

        return http.build();
    }
	
	@Autowired
    private CustomUserDetailsService userDetailsService;
	
	@Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        return http.getSharedObject(AuthenticationManagerBuilder.class)
                .userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder())
                .and()
                .build();
    }
	
}

