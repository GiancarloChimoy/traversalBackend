package com.prueba.trv.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // Deshabilitar CSRF
                .cors(cors -> cors.configurationSource(request -> {
                    CorsConfiguration config = new CorsConfiguration();
                    config.applyPermitDefaultValues(); // Aplica los valores predeterminados permitidos
                    config.addAllowedOrigin("http://localhost:3000"); // URL de tu frontend
                    config.addAllowedOrigin("http://localhost:3001"); // URL de tu frontend
                    config.addAllowedMethod("GET"); // Permitir GET
                    config.addAllowedMethod("POST"); // Permitir POST
                    config.addAllowedMethod("PUT"); // Permitir PUT
                    config.addAllowedMethod("DELETE"); // Permitir DELETE
                    config.addAllowedMethod("OPTIONS"); // Asegúrate de permitir OPTIONS
                    config.addAllowedHeader("*"); // Permitir todas las cabeceras
                    return config;
                }))
                .authorizeHttpRequests(authorizeRequests -> authorizeRequests
                        .requestMatchers(HttpMethod.POST, "/quote/").permitAll()
                        .requestMatchers(HttpMethod.GET, "/contact/**", "/content/","/banner/").permitAll()
                        .requestMatchers("/product/", "/auth/**").permitAll()
                        .anyRequest().authenticated())
                .userDetailsService(customUserDetailsService)
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
