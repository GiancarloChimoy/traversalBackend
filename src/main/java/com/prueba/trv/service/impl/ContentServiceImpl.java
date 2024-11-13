package com.prueba.trv.service.impl;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.List;
import java.util.NoSuchElementException;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.prueba.trv.entity.Content;
import com.prueba.trv.repository.ContentRepository;
import com.prueba.trv.service.ContentService;

@Service
public class ContentServiceImpl implements ContentService {

    @Autowired
    private ContentRepository repository;

    @Override
    public void delete(ObjectId id) {
        // TODO Auto-generated method stub
        try {
            Content registro = repository.findById(id)
                    .orElseThrow(() -> new NoSuchElementException("Contenido no encontrado"));
            repository.delete(registro);
        } catch (Exception e) {
            // Manejar la excepción si es necesario
            System.err.println(e.getMessage());
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<Content> findAll(Pageable page) {
        // TODO Auto-generated method stub
        try {
            return repository.findAll(page).toList();
        } catch (Exception e) {
            return null;
        }

    }

    @Override
    @Transactional(readOnly = true)
    public Content findById(ObjectId id) {
        // TODO Auto-generated method stub
        try {
            return repository.findById(id).orElseThrow(() -> new NoSuchElementException("Contenido no encontrado"));
        } catch (Exception e) {
            // Manejar la excepción si es necesario
            System.err.println(e.getMessage());
            return null;
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<Content> findByTitle(String title, Pageable page) {
        // TODO Auto-generated method stub
        try {
            return repository.findByTitleContaining(title, page);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public Content save(Content content, MultipartFile imageFile) throws IOException {
        // TODO Auto-generated method stub
        try {
            if (imageFile != null && !imageFile.isEmpty()) {
                String imageBase64 = Base64.getEncoder().encodeToString(imageFile.getBytes());
                content.setImagen(imageBase64);
            }
            return repository.save(content);
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return null;
        }
    }

    @Override
    public Content update(Content content, MultipartFile imageFile) throws IOException {
        try {
            // Buscar el contenido existente en la base de datos por su ID
            Content registro = repository.findById(content.getId())
                    .orElseThrow(() -> new NoSuchElementException("Contenido no encontrado"));

            // Actualizar los campos con los nuevos valores
            registro.setType(content.getType());
            registro.setTitle(content.getTitle());
            registro.setContent(content.getContent());
            registro.setLastUpdated(LocalDateTime.now());

            // Si se proporciona una nueva imagen, actualízala
            if (imageFile != null && !imageFile.isEmpty()) {
                String imageBase64 = Base64.getEncoder().encodeToString(imageFile.getBytes());
                registro.setImagen(imageBase64);
            }

            // Guardar los cambios en la base de datos
            return repository.save(registro);
        } catch (Exception e) {
            System.err.println("Error al actualizar el contenido: " + e.getMessage());
            throw new IOException("Error al actualizar el contenido", e);
        }
    }

}
