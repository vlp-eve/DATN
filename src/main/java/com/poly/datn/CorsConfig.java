package com.poly.datn;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**") // Cho phép các endpoint bắt đầu bằng /api/
                .allowedOrigins("http://localhost:63342") // Địa chỉ frontend
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // Các phương thức HTTP cho phép
                .allowedHeaders("*") // Cho phép tất cả các tiêu đề
                .allowCredentials(true); // Cho phép gửi cookies hoặc HTTP auth
    }
}
//http://localhost:3000
