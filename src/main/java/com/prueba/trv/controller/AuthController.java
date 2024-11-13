package com.prueba.trv.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.xml.bind.DatatypeConverter;

import com.prueba.trv.service.AuthService;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "http://localhost:3000")
public class AuthController {
    @Autowired
    private AuthService service;

    // Ruta para el login
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestParam String email, @RequestParam String password) {
        try {
            // Llamamos al servicio para obtener el JWT
            String token = service.login(email, password);

            // Retornamos el token JWT
            return ResponseEntity.ok(token);
        } catch (RuntimeException e) {
            // Si ocurre un error (por ejemplo, usuario no encontrado o credenciales incorrectas)
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
