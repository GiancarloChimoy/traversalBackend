package com.prueba.trv.repository;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.prueba.trv.entity.Content;

@Repository
public interface ContentRepository extends MongoRepository<Content, ObjectId> {
    List<Content> findByTitleContaining(String title, Pageable page);
}
