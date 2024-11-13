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

import com.prueba.trv.entity.BannerImage;
import com.prueba.trv.repository.BannerImageRepository;
import com.prueba.trv.service.BannerImageService;

@Service
public class BannerImageServiceImpl implements BannerImageService {

    @Autowired BannerImageRepository repository;

    @Override
    public void delete(ObjectId id) {
        // TODO Auto-generated method stub
        try {
            BannerImage registro = repository.findById(id).orElseThrow(() -> new NoSuchElementException("Banner no encontrado"));
            repository.delete(registro);
        } catch (Exception e) {
            // Manejar la excepción si es necesario
            System.err.println(e.getMessage());
        }
        
    }

    @Override
    @Transactional(readOnly = true)
    public List<BannerImage> findAll(Pageable page) {
        // TODO Auto-generated method stub
        try {
            return repository.findAll(page).toList();
        } catch (Exception e) {
            return null;
        }   
    }

    @Override
    @Transactional(readOnly = true)
    public BannerImage findById(ObjectId id) {
        // TODO Auto-generated method stub
        try {
            return repository.findById(id).orElseThrow(() -> new NoSuchElementException("Banner no encontrado"));
        } catch (Exception e) {
            // Manejar la excepción si es necesario
            System.err.println(e.getMessage());
            return null;
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<BannerImage> findByUploadedAtAfter(LocalDateTime uploadedAt, Pageable page) {
        // TODO Auto-generated method stub
        try {
            return repository.findByUploadedAtAfter(uploadedAt, page);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public BannerImage save(BannerImage bannerImage, MultipartFile imageFile) throws IOException {
        // TODO Auto-generated method stub
        try {
            if (imageFile != null && !imageFile.isEmpty()) {
                String imageBase64 = Base64.getEncoder().encodeToString(imageFile.getBytes());
                bannerImage.setImageUrl(imageBase64);
            }
            return repository.save(bannerImage);
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return null;
        }
    }

    @Override
    public BannerImage update(BannerImage bannerImage, MultipartFile imageFile) throws IOException {
        try {
            BannerImage registro = repository.findById(bannerImage.getId())
                .orElseThrow(() -> new NoSuchElementException("banner Imagen no encontrado"));
            registro.setAltText(bannerImage.getAltText());
            registro.setActive(bannerImage.isActive());

            // Actualiza la imagen solo si se proporciona una nueva
            if (imageFile != null && !imageFile.isEmpty()) {
                String imageBase64 = Base64.getEncoder().encodeToString(imageFile.getBytes());
                registro.setImageUrl(imageBase64);
            }
            return repository.save(registro);
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return null;
        }
    }
    
}
