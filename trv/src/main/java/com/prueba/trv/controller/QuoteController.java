package com.prueba.trv.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.prueba.trv.entity.Quote;
import com.prueba.trv.service.QuoteService;

@RestController
@RequestMapping("/quote/")
@CrossOrigin(origins = "http://localhost:3000")
public class QuoteController {

    @Autowired
    private QuoteService service;

    @GetMapping
    public ResponseEntity<List<Quote>> findAll(
            @RequestParam(value = "date", required = false) LocalDateTime date,
            @RequestParam(value = "offset", required = false, defaultValue = "0") int pageNumber,
            @RequestParam(value = "limit", required = false, defaultValue = "100") int pageSize) {

        Pageable page = PageRequest.of(pageNumber, pageSize);
        List<Quote> quotes;

        // Si no se proporciona una fecha, se obtienen todas las cotizaciones
        if (date == null) {
            quotes = service.findAll(page);
        } else {
            // Si se proporciona una fecha, se busca por fecha
            quotes = service.findByDate(date, page);
        }

        // Si no se encuentran cotizaciones, se responde con "no content"
        if (quotes.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(quotes);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Quote> findById(@PathVariable("id") ObjectId id) {
        Quote registro = service.findById(id);
        if (registro == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(registro);
    }

    @PostMapping
    public ResponseEntity<Quote> create(@RequestBody Quote quote) {
        // Asignar un valor predeterminado al estado "1" (enviado)
        if (quote.getState() == 0) { // Si el estado no está especificado, asignar 1
            quote.setState(1); // "1" representando "enviado"
        }
        // Guardar la cotización con el estado predeterminado
        Quote registro = service.save(quote);
        return ResponseEntity.status(HttpStatus.CREATED).body(registro);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Quote> update(
            @PathVariable("id") ObjectId id,
            @RequestBody Quote quote) {

        // Aquí puedes establecer el id en el objeto contact si es necesario.
        quote.setId(id);

        // Actualiza la cotización en el servicio
        Quote registro = service.update(quote);

        if (registro == null) {
            return ResponseEntity.notFound().build(); // Si no se encuentra el contacto
        }
        return ResponseEntity.ok(registro); // Retorna el contacto actualizado
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") ObjectId id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}/state")
    public ResponseEntity<Quote> updateState(
            @PathVariable("id") ObjectId id,
            @RequestParam("state") int state) {

        // Verificar si el estado es válido
        if (state < 1 || state > 4) {
            return ResponseEntity.badRequest().body(null); // Puedes retornar un error si el estado es inválido
        }

        // Buscar la cotización por su ID
        Quote existingQuote = service.findById(id);
        if (existingQuote == null) {
            return ResponseEntity.notFound().build(); // Si no se encuentra la cotización, retornar 404
        }

        // Actualizar solo el estado
        existingQuote.setState(state);

        // Guardar la cotización con el estado actualizado
        Quote updatedQuote = service.update(existingQuote);

        return ResponseEntity.ok(updatedQuote); // Retornar la cotización actualizada
    }

}
