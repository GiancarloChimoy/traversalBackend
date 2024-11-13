package com.prueba.trv.service.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.prueba.trv.entity.Quote;
import com.prueba.trv.repository.QuoteRepository;
import com.prueba.trv.service.QuoteService;

@Service
public class QuoteServiceImpl implements QuoteService {

    @Autowired
    private QuoteRepository repository;

    @Override
    public void delete(ObjectId id) {
        // TODO Auto-generated method stub
        try {
            Quote registro = repository.findById(id)
                    .orElseThrow(() -> new NoSuchElementException("Cotización no encontrado"));
            repository.delete(registro);
        } catch (Exception e) {
            // Manejar la excepción si es necesario
            System.err.println(e.getMessage());
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<Quote> findAll(Pageable page) {
        // TODO Auto-generated method stub
        try {
            return repository.findAll(page).toList();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<Quote> findByDate(LocalDateTime date, Pageable page) {
        // TODO Auto-generated method stub
        try {
            return repository.findByDateContaining(date, page);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Quote findById(ObjectId id) {
        // TODO Auto-generated method stub
        try {
            return repository.findById(id).orElseThrow(() -> new NoSuchElementException("Cotización no encontrado"));
        } catch (Exception e) {
            // Manejar la excepción si es necesario
            System.err.println(e.getMessage());
            return null;
        }
    }

    @Override
    public Quote save(Quote quote) {
        // TODO Auto-generated method stub
        try {
            Quote registro = repository.save(quote);
            return registro;
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return null; // Manejar la excepción según tus necesidades
        }
    }

    @Override
    public Quote update(Quote quote) {
        // TODO Auto-generated method stub
        try {
            Quote registro = repository.findById(quote.getId()).orElseThrow();
            registro.setName(quote.getName());
            registro.setPhone(quote.getPhone());
            registro.setEmail(quote.getEmail());
            registro.setDescription(quote.getDescription());

            repository.save(registro);
            return registro;
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return null;
        }
    }

    @Override
    public Quote updateState(ObjectId id, int newState) {
        // TODO Auto-generated method stub
        Quote quote = repository.findById(id).orElse(null);

        if (quote != null) {
            // Actualizar solo el estado
            quote.setState(newState);
            return repository.save(quote); // Guardar los cambios en el repositorio
        } else{
            return null; // Retornar null si no se encuentra la cotización
        }
    }

}
