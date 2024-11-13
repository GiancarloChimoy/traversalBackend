package com.prueba.trv.entity;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Document(collection = "contact")
public class Contact {
    @Id
    private ObjectId id;
    @NotBlank
    @Email
    private String email;
    @NotBlank
    private String whatsapp;
    @NotBlank
    private String facebook;
    @NotBlank
    private String address;
    
}
