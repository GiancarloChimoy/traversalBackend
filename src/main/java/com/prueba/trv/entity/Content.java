package com.prueba.trv.entity;

import java.time.LocalDateTime;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Document(collection = "content")
public class Content {
    @Id
    private ObjectId id;
    @NotNull
    @Min(1)
    @Max(4)
    private int type;
    @NotBlank
    private String title; // Ej. "Nuestra Misión" o "¿Quiénes somos?"
    @NotBlank
    private String content; // Texto de la sección
    @Lob
    private String imagen;
    private LocalDateTime lastUpdated;
}
