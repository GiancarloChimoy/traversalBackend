package com.prueba.trv.entity;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

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
@Document(collection = "product")
public class Product {
    @Id
    private ObjectId id;
    @NotBlank
    @Indexed(unique = true)
    private String code;
    @NotBlank
    private String name;
    @NotBlank
    private String type;
    @NotBlank
    private String description;
    @NotNull
    private float price; 
    private float offer;
    
    @Lob
    private String imagen;

    public Product(String id) {
        this.id = new ObjectId(id);
    }
}
