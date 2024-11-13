package com.prueba.trv.service;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.domain.Pageable;

import com.prueba.trv.entity.User;

public interface UserService {
     // Crear un nuevo usuario
    User save(User user);

    // Obtener todos los usuarios (con paginación)
    List<User> findAll(Pageable page);

    // Buscar un usuario por ID
    User findById(ObjectId id);

    // Actualizar los datos de un usuario
    User update(User user);

    // Buscar usuarios por parte de su DNI (con paginación)
    List<User> findByDni(String dni, Pageable page);

    // Eliminar un usuario
    void delete(ObjectId id);

    // Método para actualizar el estado de la cuenta
    User updateAccountStatus(ObjectId id, boolean newState);
}
