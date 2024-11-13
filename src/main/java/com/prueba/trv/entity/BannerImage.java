package com.prueba.trv.entity;

import java.time.LocalDateTime;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.persistence.Id;
import jakarta.persistence.Lob;
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
@Document(collection = "bannerImages")
public class BannerImage {
    @Id
    private ObjectId id;

    @NotBlank
    @Lob
    private String imageUrl; // URL de la imagen
    private String altText; // Texto alternativo para la imagen
    @NotNull
    private LocalDateTime uploadedAt; // Fecha de carga de la imagen
    @NotNull
    private boolean active;
}
