package com.prueba.trv.repository;

import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.prueba.trv.entity.Product;

@Repository
public interface ProductRepository extends MongoRepository<Product, ObjectId> {
    List<Product> findByNameContaining(String name, Pageable page);
    // Método para buscar un producto por su código (code)
    Optional<Product> findByCode(String code);  // Si code es único
}
