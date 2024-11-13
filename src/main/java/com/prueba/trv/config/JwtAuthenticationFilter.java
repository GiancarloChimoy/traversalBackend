package com.prueba.trv.config;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.prueba.trv.service.KeyGeneratorService;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private KeyGeneratorService keyGeneratorService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {
        // Obtener el token de la cabecera Authorization
        String token = request.getHeader("Authorization");

        if (request.getRequestURI().startsWith("/auth/**")) {
            // Si es una ruta pública, continuar sin hacer nada
            chain.doFilter(request, response);
            return;
        }
        if (token != null && token.startsWith("Bearer ")) {
            // Extraer el JWT (eliminar "Bearer " del token)
            token = token.substring(7);

            try {
                // Validar el JWT y extraer el contenido
                Claims claims = Jwts.parser()
                        .verifyWith(keyGeneratorService.getKey())
                        .build()
                        .parseSignedClaims(token)
                        .getPayload(); // Obtenemos los Claims // Obtener los Claims

                // Extraer el ID del usuario del token
                String userId = claims.getSubject();

                // Crear el objeto de autenticación
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userId,
                        null, null);

                // Establecer los detalles de la autenticación
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                // Establecer el contexto de seguridad
                SecurityContextHolder.getContext().setAuthentication(authentication);
            } catch (JwtException e) { // Capturamos JwtException en lugar de SignatureException
                // Si el token no es válido, devolver una respuesta de error
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getWriter().write("Token inválido");
                return;
            }
        }

        // Continuar con la cadena de filtros
        chain.doFilter(request, response);
    }
}
