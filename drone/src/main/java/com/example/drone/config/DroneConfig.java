package com.example.drone.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class DroneConfig {
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        // @formatter:off
        return http
                .formLogin()
                .and()
                .headers()
                .frameOptions()
                .sameOrigin()
                .and()
                .cors()
                .and()
                .exceptionHandling()
                .and()
                .csrf()
                .disable()
                .authorizeRequests()
                .antMatchers(
                        "/login",
                        "/**/drone")
                .permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.ALWAYS)
                .and().build();

        // @formatter:on
    }
}
