package com.prueba.trv.service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import com.prueba.trv.entity.BannerImage;


public interface BannerImageService {

    // Método para obtener todas las imágenes de banner con paginación
    List<BannerImage> findAll(Pageable page);

    // Método para buscar imágenes de banner por fecha de carga
    List<BannerImage> findByUploadedAtAfter(LocalDateTime uploadedAt, Pageable page);

    // Método para encontrar una imagen de banner por su ID
    BannerImage findById(ObjectId id);

    // Método para guardar una nueva imagen de banner
    BannerImage save(BannerImage bannerImage, MultipartFile imageFile) throws IOException;

    // Método para actualizar una imagen de banner existente
    BannerImage update(BannerImage bannerImage, MultipartFile imageFile) throws IOException;

    // Método para eliminar una imagen de banner por su ID
    void delete(ObjectId id);
}
