package com.prueba.trv.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.prueba.trv.entity.BannerImage;

@Repository
public interface BannerImageRepository extends MongoRepository<BannerImage, ObjectId> {
    List<BannerImage> findByUploadedAtAfter(LocalDateTime uploadedAt, Pageable page);

}
