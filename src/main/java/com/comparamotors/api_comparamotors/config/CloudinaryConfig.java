package com.comparamotors.api_comparamotors.config;

import com.cloudinary.Cloudinary;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class CloudinaryConfig {
    
    @Value("${cloudinary.cloud-name}")
    private String cloudName;
    
    @Value("${cloudinary.api-key}")
    private String apiKey;
    
    @Value("${cloudinary.api-secret}")
    private String apiSecret;

    @Bean
    public Cloudinary cloudinary() {
        // Usar el formato URL de Cloudinary directamente
        String cloudinaryUrl = String.format("cloudinary://%s:%s@%s", 
            apiKey, apiSecret, cloudName);
        return new Cloudinary(cloudinaryUrl);
    }
}