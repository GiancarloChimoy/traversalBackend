package com.prueba.trv.service;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.domain.Pageable;


import com.prueba.trv.entity.Contact;


public interface ContactService {
    List<Contact> findAll(Pageable page);

    List<Contact> findByEmail(String email, Pageable page);

    Contact findById(ObjectId id);

    Contact save(Contact contact);

    Contact update(Contact contact);

    void delete(ObjectId id);
}
