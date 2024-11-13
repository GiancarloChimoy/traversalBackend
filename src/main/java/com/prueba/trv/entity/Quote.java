package com.prueba.trv.entity;

import java.time.LocalDateTime;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import jakarta.validation.constraints.Email;
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
@Document(collection = "quote")
public class Quote {
    @Id
    private ObjectId id;
    @NotBlank
    @Indexed(unique = true)
    private String productCode;
    @NotBlank
    private String name;
    private String phone;
    @Email
    private String email;
    @NotNull
    private int quantity;
    @NotBlank
    private String description;
    @NotNull
    @Min(1)
    @Max(4)
    private int state;
    @NotNull
    @Field("date")
    private LocalDateTime date;
}
