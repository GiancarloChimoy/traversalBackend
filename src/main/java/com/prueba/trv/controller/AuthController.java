package com.prueba.trv.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.prueba.trv.service.AuthService;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "http://localhost:3001")
public class AuthController {

    @Autowired
    private AuthService service;

    // Clase de ayuda para la solicitud de login
    public static class LoginRequest {
        public String email;
        public String password;
    }

    // Ruta para el login
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest request) {
        try {
            // Llamamos al servicio para obtener el JWT
            String token = service.login(request.email, request.password);

            // Retornamos el token JWT
            return ResponseEntity.ok(token);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
