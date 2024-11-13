package com.prueba.trv.repository;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.prueba.trv.entity.Contact;

@Repository
public interface ContactRepository extends MongoRepository<Contact, ObjectId>{
    List<Contact> findByEmailContaining(String email, Pageable page);
}
