package com.Surakuri.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import java.util.Arrays;
import java.util.Collections;

import jakarta.servlet.http.HttpServletRequest;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                // 1. Session Management: Stateless (Best for APIs/JWT)
                .sessionManagement(management -> management.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                // 2. URL Permissions
                .authorizeHttpRequests(Authorize -> Authorize

                        // 1. Public Endpoints (Auth & Sellers)
                        .requestMatchers("/auth/**").permitAll()
                        .requestMatchers("/sellers/**").permitAll()

                        // 2. Testing Endpoints (Open for Dev)
                        .requestMatchers("/api/products/**").permitAll()
                        .requestMatchers("/api/cart/**").permitAll()
                        .requestMatchers("/api/orders/**").permitAll() // <--- ADD THIS NEW LINE!


                        // 3. Locked Endpoints (Specific)
                        // .requestMatchers("/api/admin/**").hasRole("ADMIN")

                        // 4. The "Catch-All" (MUST BE LAST)
                        .anyRequest().permitAll() // Or .authenticated() if you want to lock everything else


                )

                // 3. Disable CSRF (Not needed for stateless APIs)
                .csrf(csrf -> csrf.disable())

                // 4. CORS Configuration (Allow Frontend to talk to Backend)
                .cors(cors -> cors.configurationSource(new CorsConfigurationSource() {
                    @Override
                    public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
                        CorsConfiguration cfg = new CorsConfiguration();
                        cfg.setAllowedOrigins(Arrays.asList(
                                "http://localhost:3000", // React Default
                                "http://localhost:5173"  // Vite/Vue Default
                        ));
                        cfg.setAllowedMethods(Collections.singletonList("*"));
                        cfg.setAllowCredentials(true);
                        cfg.setAllowedHeaders(Collections.singletonList("*"));
                        cfg.setExposedHeaders(Arrays.asList("Authorization"));
                        cfg.setMaxAge(3600L);
                        return cfg;
                    }
                }));

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}