package com.prueba.trv.service;

import java.io.IOException;
import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import com.prueba.trv.entity.Product;

public interface ProductService {
    List<Product> findAll(Pageable page);

    List<Product> findByName(String name, Pageable page);

    Product findById(ObjectId id);

    Product save(Product product, MultipartFile imageFile) throws IOException;

    Product update(Product product, MultipartFile imageFile) throws IOException;

    void delete(ObjectId id);
}
