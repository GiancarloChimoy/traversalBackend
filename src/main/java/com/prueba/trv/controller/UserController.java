package com.prueba.trv.controller;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.prueba.trv.entity.User;
import com.prueba.trv.service.UserService;

@RestController
@RequestMapping("/user/")
@CrossOrigin(origins = "http://localhost:3000")
public class UserController {

    @Autowired
    private UserService service;

    @GetMapping()
    public ResponseEntity<List<User>> findAll(
            @RequestParam(value = "dni", required = false, defaultValue = " ") String dni,
            @RequestParam(value = "offset", required = false, defaultValue = "0") int pageNumber,
            @RequestParam(value = "limit", required = false, defaultValue = "5") int pageSize) {

        Pageable page = PageRequest.of(pageNumber, pageSize);
        List<User> users;
        if (dni == null) {
            users = service.findAll(page);

        } else {
            users = service.findByDni(dni, page);
        }

        if (users.isEmpty()) {
            return ResponseEntity.noContent().build();

        }
        return ResponseEntity.ok(users);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<User> findById(@PathVariable("id") ObjectId id) {

        User registro = service.findById(id);
        if (registro == null) {
            return ResponseEntity.notFound().build();

        }
        return ResponseEntity.ok(registro);

    }

    @PostMapping()
    public ResponseEntity<User> create(@RequestBody User user) {
        User registro = service.save(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(registro);
    }

}
