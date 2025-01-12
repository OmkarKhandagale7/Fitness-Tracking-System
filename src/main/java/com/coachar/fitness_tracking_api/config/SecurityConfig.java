package com.coachar.fitness_tracking_api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    // Define PasswordEncoder
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // Define AuthenticationManager bean
    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http, PasswordEncoder passwordEncoder) throws Exception {
        return http.getSharedObject(AuthenticationManagerBuilder.class)
            .inMemoryAuthentication()
            .withUser("admin")
            .password(passwordEncoder.encode("admin123"))
            .roles("ADMIN")
            .and()
            .withUser("user")
            .password(passwordEncoder.encode("user123"))
            .roles("USER")
            .and()
            .and()
            .build();
    }

    // Define SecurityFilterChain bean
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf().disable()
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/api/users/**").hasRole("ADMIN") // Only ADMIN can access user endpoints
                .requestMatchers("/api/workout-plans/**", "/api/activity-logs/**").hasAnyRole("USER", "ADMIN") // USER and ADMIN can access these endpoints
                .anyRequest().authenticated() // All other requests require authentication
            )
            .httpBasic(); // Enable basic authentication
        return http.build();
    }
}
