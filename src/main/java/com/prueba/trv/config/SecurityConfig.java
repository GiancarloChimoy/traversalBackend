package com.prueba.trv.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Autowired
    private CustomUserDetailsService customUserDetailsService; // Inyectar tu servicio de detalles de usuario

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // Use the new syntax to disable CSRF
                .authorizeHttpRequests(authorizeRequests -> authorizeRequests
                        .requestMatchers("/product/", "/auth/**").permitAll() // Permitir acceso público a login y
                                                                              // registro
                        .anyRequest().authenticated() // Requerir autenticación para todas las demás solicitudes
                )
                .userDetailsService(customUserDetailsService) // Configurar el UserDetailsService
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class); // Agregar el
                                                                                                       // filtro JWT

        return http.build();
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder(); // Para encriptar las contraseñas
    }
}
