package com.carpooling.common;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfiguration {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())          // CSRF komplett aus
                .authorizeHttpRequests(auth -> auth
                        .anyRequest().permitAll()           // Alle Anfragen erlauben, ohne Login
                )
                .formLogin(form -> form.disable())      // Login deaktiviert
                .httpBasic(basic -> basic.disable());   // Basic Auth auch deaktiviert
        return http.build();
    }
}
