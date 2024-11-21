package com.prueba.trv.service;

import java.time.LocalDateTime;
import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.domain.Pageable;

import com.prueba.trv.entity.Quote;

public interface QuoteService {
    List<Quote> findAll(Pageable page);

    List<Quote> findByDate(LocalDateTime date, Pageable page);

    Quote findById(String id);

    Quote save(Quote quote);

    Quote update(Quote quote);

    // Nuevo método para actualizar solo el estado
    Quote updateState(String id, int newState);

    void delete(String id);
}
