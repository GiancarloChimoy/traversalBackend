package com.prueba.trv.config;


import javax.crypto.SecretKey;

import io.jsonwebtoken.Jwts;

public class KeyGeneratorExample {
    public static void main(String[] args) {
        // Genera una clave segura adecuada para HS256
        SecretKey key = Jwts.SIG.HS256.key().build();
        System.out.println("Generated Key: " + java.util.Base64.getEncoder().encodeToString(key.getEncoded()));
    }
}
