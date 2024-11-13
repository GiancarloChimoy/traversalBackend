package com.prueba.trv.controller;

import java.io.IOException;
import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.prueba.trv.entity.Content;
import com.prueba.trv.service.ContentService;

@RestController
@RequestMapping("/content/")
@CrossOrigin(origins = "http://localhost:3000")
public class ContentController {

    @Autowired
    private ContentService service;

    @GetMapping
    public ResponseEntity<List<Content>> findAll(
            @RequestParam(value = "title", required = false, defaultValue = "") String title,
            @RequestParam(value = "offset", required = false, defaultValue = "0") int pageNumber,
            @RequestParam(value = "limit", required = false, defaultValue = "5") int pageSize) {

        Pageable page = PageRequest.of(pageNumber, pageSize);
        List<Content> contents;

        if (title == null || title.trim().isEmpty()) {
            contents = service.findAll(page);
        } else {
            contents = service.findByTitle(title, page);
        }

        if (contents.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(contents);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Content> findById(@PathVariable("id") ObjectId id) {
        Content registro = service.findById(id);
        if (registro == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(registro);
    }

    @PostMapping(consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    public ResponseEntity<Content> create(
            @RequestPart("content") Content content,
            @RequestPart(value = "imageFile", required = false) MultipartFile imageFile) {

        try {
            Content registro = service.save(content, imageFile);
            return ResponseEntity.status(HttpStatus.CREATED).body(registro);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping(value = "/{id}", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    public ResponseEntity<Content> update(
            @PathVariable("id") ObjectId id,
            @RequestPart("content") Content content,
            @RequestPart(value = "imageFile", required = false) MultipartFile imageFile) {

        try {
            content.setId(id); // Asegúrate de que el ID está configurado en el objeto Content
            Content registro = service.update(content, imageFile);
            if (registro == null) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(registro);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") ObjectId id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
