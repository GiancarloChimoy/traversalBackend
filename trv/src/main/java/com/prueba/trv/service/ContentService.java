package com.prueba.trv.service;

import java.io.IOException;
import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import com.prueba.trv.entity.Content;


public interface ContentService {

    List<Content> findAll(Pageable page);

    List<Content> findByTitle(String title, Pageable page);

    Content findById(ObjectId id);

    Content save(Content content, MultipartFile imageFile) throws IOException;

    Content update(Content content, MultipartFile imageFile) throws IOException;

    void delete(ObjectId id);
}
