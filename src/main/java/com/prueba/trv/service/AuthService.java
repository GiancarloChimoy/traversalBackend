package com.prueba.trv.service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.MacAlgorithm;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;


import com.prueba.trv.entity.User;
import com.prueba.trv.repository.UserRepository;

import java.security.Key;
import java.util.Date;

@Service
public class AuthService {

    @Autowired
    private UserRepository repository;  // Repositorio para buscar usuarios

    @Autowired
    private BCryptPasswordEncoder passwordEncoder; // Para verificar las contraseñas

    private static final String SECRET_KEY = "my_secret_key"; // Clave secreta para JWT (debes cambiarla en producción)

    public String login(String email, String password) {
        // Buscar al usuario por su email
        User user = repository.findByEmail(email);
        if (user == null) {
            throw new RuntimeException("Usuario no encontrado");
        }

        // Verificar que la contraseña sea correcta
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new RuntimeException("Credenciales incorrectas");
        }

        // Generar el token JWT
        return generateToken(user);
    }

    private String generateToken(User user) {
        // Definir la fecha de expiración del token (por ejemplo, 1 hora)
        long expirationTime = 1000 * 60 * 60;

        // Convertir ObjectId a String
        String userId = user.getId().toHexString();
        Key key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes());

        // Crear el token
        return Jwts.builder()
            .subject(userId)  // Usando el nuevo método 'subject'
            .issuedAt(new Date())  // Fecha de emisión
            .expiration(new Date(System.currentTimeMillis() + expirationTime))   // Fecha de expiración
            .signWith(key)  // Firma con la clave secreta
            .compact();
    }
}

