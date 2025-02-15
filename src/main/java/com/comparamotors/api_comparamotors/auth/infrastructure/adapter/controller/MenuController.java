package com.comparamotors.api_comparamotors.auth.infrastructure.adapter.controller;

import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.comparamotors.api_comparamotors.auth.application.port.output.UserRepository;
import com.comparamotors.api_comparamotors.auth.domain.model.Permission;
import com.comparamotors.api_comparamotors.auth.domain.model.User;
import com.comparamotors.api_comparamotors.auth.infrastructure.adapter.cache.PermissionsCacheService;

@RestController
@RequestMapping("/menu")
public class MenuController {
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private PermissionsCacheService permissionsCacheService;
    
    @GetMapping("/access")
    public ResponseEntity<?> getUserPermissions(Authentication authentication) {
        // Forzar la recarga del caché
        permissionsCacheService.evictAllCache();
        
        String username = authentication.getName();
        User user = userRepository.findByUsername(username)
            .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));
            
        Set<String> permissions = user.getRoles().stream()
            .flatMap(role -> role.getPermissions().stream())
            .map(Permission::getName)
            .collect(Collectors.toSet());
            
        return ResponseEntity.ok(permissions);
    }
}