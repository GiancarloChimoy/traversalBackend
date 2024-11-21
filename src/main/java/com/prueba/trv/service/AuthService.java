package com.prueba.trv.service;

import io.jsonwebtoken.Jwts;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.prueba.trv.entity.User;
import com.prueba.trv.repository.UserRepository;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.SecretKey;

import javax.crypto.SecretKey;

@Service
public class AuthService {

    @Autowired
    private UserRepository repository; // Repositorio para buscar usuarios

    @Autowired
    private BCryptPasswordEncoder passwordEncoder; // Para verificar las contraseñas

    @Autowired
<<<<<<< HEAD
    private KeyGeneratorService keyGeneratorService; // Inyección del servicio de generación de clave
=======
    private KeyGeneratorService keyGeneratorService;  // Inyección del servicio de generación de clave
>>>>>>> 1284a29a75ca7aeea50ea020345f375f2123dd05

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

        // Obtener la clave generada a través del servicio
        SecretKey key = keyGeneratorService.getKey();
<<<<<<< HEAD

        Map<String, Object> claims = new HashMap<>();
        claims.put("name", user.getName());
        claims.put("type", user.getType());
        claims.put("state", user.isState());

        // Crear el token
        return Jwts.builder()
                .claims(claims)
                .subject(userId) // Usando el nuevo método 'subject'
                .issuedAt(new Date()) // Fecha de emisión
                .expiration(new Date(System.currentTimeMillis() + expirationTime)) // Fecha de expiración
                .signWith(key) // Firma con la clave secreta inyectada
                .compact();
=======

        // Crear el token
        return Jwts.builder()
            .subject(userId)  // Usando el nuevo método 'subject'
            .issuedAt(new Date())  // Fecha de emisión
            .expiration(new Date(System.currentTimeMillis() + expirationTime))   // Fecha de expiración
            .signWith(key)  // Firma con la clave secreta inyectada
            .compact();
>>>>>>> 1284a29a75ca7aeea50ea020345f375f2123dd05
    }
}
