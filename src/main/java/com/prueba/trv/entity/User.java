package com.prueba.trv.entity;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

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
@Document(collection = "user")
public class User {
    @Id
    private ObjectId id;
    @NotBlank
    private String name;
    @NotBlank
    private String lastname;
    @NotBlank
    @Indexed(unique = true)
    private String code;
    @NotBlank
    @Indexed(unique = true)
    private String dni;
    @NotBlank
    private String phone;
    @Email
    private String email;
    @NotBlank
    @Email
    private String password;
    @NotNull
    @Min(1)
    @Max(2)
    private int type;
    @NotNull
    private boolean state;
    public static Object builder() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'builder'");
    }

}
