package com.prueba.trv.controller;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.prueba.trv.entity.BannerImage;
import com.prueba.trv.service.BannerImageService;

@RestController
@RequestMapping("/banner/")
@CrossOrigin(origins = "http://localhost:3000")
public class BannerImageController {
    
    @Autowired
    private BannerImageService service;

    @GetMapping
    public ResponseEntity<List<BannerImage>> findAll(
            @RequestParam(value = "uploadedAt", required = false) String uploadedAt,
            @RequestParam(value = "offset", required = false, defaultValue = "0") int pageNumber,
            @RequestParam(value = "limit", required = false, defaultValue = "5") int pageSize) {

        Pageable page = PageRequest.of(pageNumber, pageSize);
        List<BannerImage> bannerImages;

        if (uploadedAt == null || uploadedAt.trim().isEmpty()) {
            bannerImages = service.findAll(page);
        } else {
            try {
                LocalDateTime uploadedAtDate = LocalDateTime.parse(uploadedAt);
                bannerImages = service.findByUploadedAtAfter(uploadedAtDate, page);
            } catch (DateTimeParseException e) {
                return ResponseEntity.badRequest().body(null);
            }
        }

        if (bannerImages.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(bannerImages);
    }

    @GetMapping("/{id}")
	public ResponseEntity<BannerImage> findById(@PathVariable("id") ObjectId id) {
		BannerImage registro = service.findById(id);
		if (registro == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(registro);
	}
    
    @PostMapping(consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
	public ResponseEntity<BannerImage> create(
            @RequestPart("bannerImage") BannerImage bannerImage, 
            @RequestPart(value = "imageFile", required = false) MultipartFile imageFile) {
			
		try {
			BannerImage registro = service.save(bannerImage, imageFile);
			return ResponseEntity.status(HttpStatus.CREATED).body(registro);
		} catch (IOException e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

    @PutMapping(value = "/{id}", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    public ResponseEntity<BannerImage> update(
            @PathVariable("id") ObjectId id, 
            @RequestPart("bannerImage") BannerImage bannerImage, 
            @RequestPart(value = "imageFile", required = false) MultipartFile imageFile) {
		
		try {
			bannerImage.setId(id);  // Asegúrate de que el ID está configurado en el objeto bannerImage
			BannerImage registro = service.update(bannerImage, imageFile);
			if (registro == null) {
				return ResponseEntity.notFound().build();
			}
			return ResponseEntity.ok(registro);
		} catch (IOException e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

    @DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable("id") ObjectId id) {
	    service.delete(id);
	    return ResponseEntity.noContent().build();
	}
}
