package com.prueba.trv.controller;

import java.io.IOException;
import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
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

import com.prueba.trv.entity.Product;
import com.prueba.trv.service.ProductService;

import org.springframework.http.MediaType;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/product/")
@CrossOrigin(origins = "http://localhost:3000")
public class ProductController {
    
    @Autowired
	private ProductService service;

    @GetMapping
	public ResponseEntity<List<Product>> findAll(
			@RequestParam(value = "name", required = false, defaultValue = "") String name,
			@RequestParam(value = "offset", required = false, defaultValue = "0") int pageNumber,
			@RequestParam(value = "limit", required = false, defaultValue = "100") int pageSize) {
		
		Pageable page = PageRequest.of(pageNumber, pageSize);
		List<Product> products;

		if (name == null || name.trim().isEmpty()) {
			products = service.findAll(page);
		} else {
			products = service.findByName(name, page);
		}
		
		if (products.isEmpty()) {
			return ResponseEntity.noContent().build();
		}
		return ResponseEntity.ok(products);
	}
	
    @GetMapping("/{id}")
	public ResponseEntity<Product> findById(@PathVariable("id") ObjectId id) {
		Product registro = service.findById(id);
		if (registro == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(registro);
	}

    @PostMapping(consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
	public ResponseEntity<Product> create(
            @RequestPart("product") Product product, 
            @RequestPart(value = "imageFile", required = false) MultipartFile imageFile) {
			
		try {
			Product registro = service.save(product, imageFile);
			return ResponseEntity.status(HttpStatus.CREATED).body(registro);
		} catch (IOException e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}
	
	@PutMapping(value = "/{id}", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    public ResponseEntity<Product> update(
            @PathVariable("id") ObjectId id, 
            @RequestPart("product") Product product, 
            @RequestPart(value = "imageFile", required = false) MultipartFile imageFile) {
		
		try {
			product.setId(id);  // Asegúrate de que el ID está configurado en el objeto Product
			Product registro = service.update(product, imageFile);
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
