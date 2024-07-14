package com.example.shop_server.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer{
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**") // Áp dụng cho tất cả các đường dẫn
                        .allowedOriginPatterns("http://localhost:5173") // Cho phép nguồn gốc này
                        .allowedMethods("GET", "POST", "PUT", "DELETE") // Cho phép các phương thức này
                        .allowedHeaders("*") // Cho phép tất cả tiêu đề
                        .allowCredentials(true); // Cho phép gửi thông tin xác thực
            }
        };
    }
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new AuthenInterceptor())

                .addPathPatterns("/authen")
            .addPathPatterns("/categories")
            .addPathPatterns("/list")
           .addPathPatterns("/update");

    }

//    @Bean
//    public WebMvcConfigurer corsConfigurer() {
//        return new WebMvcConfigurer() {
//            @Override
//            public void addCorsMappings(CorsRegistry registry) {
//                registry.addMapping("/**").allowedOrigins("*");
//            }
//        };
//    }
}