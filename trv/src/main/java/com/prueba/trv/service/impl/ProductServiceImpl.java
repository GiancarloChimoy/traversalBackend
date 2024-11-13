package com.prueba.trv.service.impl;

import java.io.IOException;
import java.util.Base64;
import java.util.List;
import java.util.NoSuchElementException;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.prueba.trv.entity.Product;
import com.prueba.trv.repository.ProductRepository;
import com.prueba.trv.service.ProductService;



@Service
public class ProductServiceImpl implements ProductService{
    
    @Autowired
    private ProductRepository repository;

   @Override
    public void delete(ObjectId id) {
        try {
            Product registro = repository.findById(id).orElseThrow(() -> new NoSuchElementException("Producto no encontrado"));
            repository.delete(registro);
        } catch (Exception e) {
            // Manejar la excepción si es necesario
            System.err.println(e.getMessage());
        }
    }


    @Override
    @Transactional(readOnly = true)
    public List<Product> findAll(Pageable page) {
        try {
            return repository.findAll(page).toList();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Product findById(ObjectId id) {
        try {
            return repository.findById(id).orElseThrow(() -> new NoSuchElementException("Producto no encontrado"));
        } catch (Exception e) {
            // Manejar la excepción si es necesario
            System.err.println(e.getMessage());
            return null;
        }
    }


    @Override
    @Transactional(readOnly = true)
    public List<Product> findByName(String name, Pageable page) {
        // TODO Auto-generated method stub
        try {
            return repository.findByNameContaining(name, page);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public Product save(Product product, MultipartFile imageFile) throws IOException {
        try {
            if (imageFile != null && !imageFile.isEmpty()) {
                String imageBase64 = Base64.getEncoder().encodeToString(imageFile.getBytes());
                product.setImagen(imageBase64);
            }
            return repository.save(product);
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return null;
        }
    }

    @Override
    public Product update(Product product, MultipartFile imageFile) throws IOException {
        try {
            Product registro = repository.findById(product.getId())
                .orElseThrow(() -> new NoSuchElementException("Producto no encontrado"));
            registro.setName(product.getName());
            registro.setType(product.getType());
            registro.setPrice(product.getPrice());
            registro.setOffer(product.getOffer());
            registro.setDescription(product.getDescription());

            // Actualiza la imagen solo si se proporciona una nueva
            if (imageFile != null && !imageFile.isEmpty()) {
                String imageBase64 = Base64.getEncoder().encodeToString(imageFile.getBytes());
                registro.setImagen(imageBase64);
            }
            return repository.save(registro);
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return null;
        }
    }

}
