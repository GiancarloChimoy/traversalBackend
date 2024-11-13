package com.prueba.trv.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.prueba.trv.entity.Quote;

@Repository
public interface QuoteRepository extends MongoRepository<Quote, ObjectId> {
    List<Quote> findByDateContaining(LocalDateTime date, Pageable page);
}
