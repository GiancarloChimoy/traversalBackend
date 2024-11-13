package com.prueba.trv.repository;

import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.prueba.trv.entity.User;

@Repository
public interface UserRepository extends MongoRepository<User, ObjectId>{
    List<User> findByDniContaining(String dni, Pageable page);
    boolean existsByDni(String dni);
    boolean existsByCode(String code);
    Optional<User> findByDni(String dni);
    Optional<User> findByCode(String code);
    User findByEmail(String email);
}
