package com.prueba.trv.controller;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.prueba.trv.entity.Contact;
import com.prueba.trv.service.ContactService;

@RestController
@RequestMapping("/contact/")
@CrossOrigin(origins = "http://localhost:3000")
public class ContactController {
    @Autowired
    private ContactService service;

    @GetMapping
	public ResponseEntity<List<Contact>> findAll(
			@RequestParam(value = "email", required = false, defaultValue = "") String email,
			@RequestParam(value = "offset", required = false, defaultValue = "0") int pageNumber,
			@RequestParam(value = "limit", required = false, defaultValue = "5") int pageSize) {
		
		Pageable page = PageRequest.of(pageNumber, pageSize);
		List<Contact> contacts;

		if (email == null || email.trim().isEmpty()) {
			contacts = service.findAll(page);
		} else {
			contacts = service.findByEmail(email, page);
		}
		
		if (contacts.isEmpty()) {
			return ResponseEntity.noContent().build();
		}
		return ResponseEntity.ok(contacts);
	}
	

    
    @GetMapping("/{id}")
    public ResponseEntity<Contact> findById(@PathVariable("id") ObjectId id) {
        Contact registro = service.findById(id);
        if (registro == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(registro);
    }

    @PostMapping()
    public ResponseEntity<Contact> create(@RequestBody Contact contact) {

        Contact registro = service.save(contact);
        return ResponseEntity.status(HttpStatus.CREATED).body(registro);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Contact> update(
        @PathVariable("id") ObjectId id, 
        @RequestBody Contact contact) {
        
        // Aquí puedes establecer el id en el objeto contact si es necesario.
        contact.setId(id);
        
        // Actualiza el contacto en el servicio
        Contact registro = service.update(contact);
        
        if (registro == null) {
            return ResponseEntity.notFound().build(); // Si no se encuentra el contacto
        }
        return ResponseEntity.ok(registro); // Retorna el contacto actualizado
    }
    

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") ObjectId id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
