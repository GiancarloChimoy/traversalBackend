package com.prueba.trv.service;

import javax.crypto.SecretKey;

import org.springframework.stereotype.Service;

import io.jsonwebtoken.Jwts;

@Service
public class KeyGeneratorService {

    private final SecretKey key;

    // Constructor que genera la clave al iniciar el servicio
    public KeyGeneratorService() {
        // Genera la clave segura adecuada para HS256
        this.key = Jwts.SIG.HS256.key().build();
        System.out.println("Generated Key: " + java.util.Base64.getEncoder().encodeToString(key.getEncoded()));
    }

    // Método para obtener la clave generada
    public SecretKey getKey() {
        return key;
    }
}
