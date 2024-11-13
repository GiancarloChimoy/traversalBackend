package com.prueba.trv.service.impl;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.prueba.trv.entity.Contact;
import com.prueba.trv.repository.ContactRepository;
import com.prueba.trv.service.ContactService;

@Service
public class ContactServiceImpl implements ContactService {

    @Autowired
    private ContactRepository repository;

    @Override
    public void delete(ObjectId id) {
        // TODO Auto-generated method stub
        try {
            Contact registro = repository.findById(id).orElseThrow();
            repository.delete(registro);
        } catch (Exception e) {
            // Manejar la excepción si es necesario
        }

    }

    @Override
    @Transactional(readOnly = true)
    public List<Contact> findAll(Pageable page) {
        // TODO Auto-generated method stub
        try {
            return repository.findAll(page).toList();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Contact findById(ObjectId id) {
        try {
            Contact registro = repository.findById(id).orElseThrow();
            return registro;
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<Contact> findByEmail(String email, Pageable page) {
        // TODO Auto-generated method stub
        try {
            return repository.findByEmailContaining(email, page);
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return null;
        }
    }

    @Override
    public Contact save(Contact contact) {
        // TODO Auto-generated method stub
        try {
            Contact registro = repository.save(contact);
            return registro;
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return null; // Manejar la excepción según tus necesidades
        }
    }

    @Override
    public Contact update(Contact contact) {
        // TODO Auto-generated method stub
        try {
            Contact registro = repository.findById(contact.getId()).orElseThrow();
            registro.setEmail(contact.getEmail());
            registro.setWhatsapp(contact.getWhatsapp());
            registro.setFacebook(contact.getFacebook());
            registro.setAddress(contact.getAddress());

            repository.save(registro);
            return registro;
        } catch (Exception e) {
            return null;
        }
    }

}
