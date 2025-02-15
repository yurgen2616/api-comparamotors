package com.comparamotors.api_comparamotors.config;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.comparamotors.api_comparamotors.auth.infrastructure.adapter.cache.PermissionsCacheService;
import com.comparamotors.api_comparamotors.config.filter.JwtAuthenticationFilter;
import com.comparamotors.api_comparamotors.config.filter.JwtValidationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    @Autowired
    private PermissionsCacheService permissionsCacheService;

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http, AuthenticationManager authManager) throws Exception {
        return http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(authz -> authz
                        // Endpoints públicos
                        .requestMatchers("/api/login").permitAll()

                        // Permitir acceso a Swagger sin autenticación
                        .requestMatchers("/swagger-ui/**", "/v3/api-docs/**").permitAll()

                        // Permisos para User
                        .requestMatchers(HttpMethod.POST, "/api/users").hasAuthority("POST /users")
                        .requestMatchers(HttpMethod.PUT, "/api/users/{id}").hasAuthority("PUT /users/{id}")
                        .requestMatchers(HttpMethod.DELETE, "/api/users/{id}").hasAuthority("DELETE /users/{id}")
                        .requestMatchers(HttpMethod.GET, "/api/users/{id}").hasAuthority("GET /users/{id}")
                        .requestMatchers(HttpMethod.GET, "/api/users").hasAuthority("GET /users")
                        .requestMatchers(HttpMethod.POST, "/api/users/{userId}/roles/{roleId}")
                        .hasAuthority("POST /users/{userId}/roles/{roleId}")
                        .requestMatchers(HttpMethod.DELETE, "/api/users/{userId}/roles/{roleId}")
                        .hasAuthority("DELETE /users/{userId}/roles/{roleId}")

                        // Permisos para Permission
                        .requestMatchers(HttpMethod.POST, "/api/permissions").hasAuthority("POST /permissions")
                        .requestMatchers(HttpMethod.PUT, "/api/permissions/{id}").hasAuthority("PUT /permissions/{id}")
                        .requestMatchers(HttpMethod.DELETE, "/api/permissions/{id}")
                        .hasAuthority("DELETE /permissions/{id}")
                        .requestMatchers(HttpMethod.GET, "/api/permissions/{id}").hasAuthority("GET /permissions/{id}")
                        .requestMatchers(HttpMethod.GET, "/api/permissions").hasAuthority("GET /permissions")

                        // Permisos para Role
                        .requestMatchers(HttpMethod.POST, "/api/roles").hasAuthority("POST /roles")
                        .requestMatchers(HttpMethod.PUT, "/api/roles/{id}").hasAuthority("PUT /roles/{id}")
                        .requestMatchers(HttpMethod.DELETE, "/api/roles/{id}").hasAuthority("DELETE /roles/{id}")
                        .requestMatchers(HttpMethod.GET, "/api/roles/{id}").hasAuthority("GET /roles/{id}")
                        .requestMatchers(HttpMethod.GET, "/api/roles").hasAuthority("GET /roles")
                        .requestMatchers(HttpMethod.POST, "/api/roles/{roleId}/permissions/{permissionId}")
                        .hasAuthority("POST /roles/{roleId}/permissions/{permissionId}")
                        .requestMatchers(HttpMethod.DELETE, "/api/roles/{roleId}/permissions/{permissionId}")
                        .hasAuthority("DELETE /roles/{roleId}/permissions/{permissionId}")

                        // Permisos para news
                        .requestMatchers(HttpMethod.GET, "/api/news").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/news/{id}").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/news/add").permitAll()
                        .requestMatchers(HttpMethod.PUT, "/api/news/{id}").permitAll()
                        .requestMatchers(HttpMethod.DELETE, "/api/news/{id}").permitAll()

                        // Permisos para ad-space de portfolio

                        .requestMatchers(HttpMethod.GET, "/api/ad-spaces").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/ad-spaces/{id}").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/ad-spaces").permitAll()
                        .requestMatchers(HttpMethod.PUT, "/api/ad-spaces/{id}").permitAll()
                        .requestMatchers(HttpMethod.DELETE, "/api/ad-spaces/{id}").permitAll()

                        // Permisos para ad-packages de portfolio

                        .requestMatchers(HttpMethod.GET, "/api/ad-packages").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/ad-packages/{id}").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/ad-packages").permitAll()
                        .requestMatchers(HttpMethod.PUT, "/api/ad-packages/{id}").permitAll()
                        .requestMatchers(HttpMethod.DELETE, "/api/ad-packages/{id}").permitAll()

                        // Permisos para ad-sales de portfolio

                        .requestMatchers(HttpMethod.GET, "/api/ad-sales").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/ad-sales/{id}").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/ad-sales/user/{userId}").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/ad-sales").permitAll()
                        .requestMatchers(HttpMethod.PUT, "/api/ad-sales/{id}").permitAll()
                        .requestMatchers(HttpMethod.DELETE, "/api/ad-sales/{id}").permitAll()

                        // Cualquier otra solicitud debe estar autenticada
                        .anyRequest().authenticated())
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilter(new JwtAuthenticationFilter(authManager))
                .addFilter(new JwtValidationFilter(authManager, permissionsCacheService))
                .build();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOrigins(Arrays.asList("http://localhost:4200")); // Ajusta según tus necesidades
        config.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE"));
        config.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type"));
        config.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }
}