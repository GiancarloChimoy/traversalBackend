package com.prueba.trv.service.impl;

import java.util.List;
import java.util.NoSuchElementException;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.prueba.trv.entity.User;
import com.prueba.trv.repository.UserRepository;
import com.prueba.trv.service.UserService;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository repository;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Override
    public User save(User user) {
        // Verificar si ya existe un usuario con el mismo DNI
        if (repository.existsByDni(user.getDni())) {
            throw new IllegalArgumentException("Ya existe un usuario con ese DNI.");
        }

        // Verificar si ya existe un usuario con el mismo código
        if (repository.existsByCode(user.getCode())) {
            throw new IllegalArgumentException("Ya existe un usuario con ese código.");
        }

        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        // Guardar el nuevo usuario
        return repository.save(user);
    }

    @Override
    public void delete(ObjectId id) {
        // Verificar si el usuario existe antes de eliminarlo
        User user = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado."));

        // Eliminar el usuario
        repository.delete(user);
    }

    @Override
    @Transactional(readOnly = true)
    public User findById(ObjectId id) {
        // Retornar el usuario si existe, o un Optional vacío si no
        try {
            return repository.findById(id).orElseThrow(() -> new NoSuchElementException("Usuario no encontrado"));
        } catch (Exception e) {
            // Manejar la excepción si es necesario
            System.err.println(e.getMessage());
            return null;
        }
    }

    @Override
    public User update(User user) {
        // Buscar el usuario por ID y lanzar una excepción si no se encuentra
        User existingUser = repository.findById(user.getId())
                .orElseThrow(() -> new NoSuchElementException("Usuario no encontrado"));

        // Actualizar los campos del usuario
        existingUser.setName(user.getName());
        existingUser.setLastname(user.getLastname());
        existingUser.setPhone(user.getPhone());
        existingUser.setEmail(user.getEmail());
        existingUser.setType(user.getType());
        if (user.getPassword() != null && !user.getPassword().isEmpty()) {
            String encodedPassword = passwordEncoder.encode(user.getPassword());
            existingUser.setPassword(encodedPassword);
        }
        // Guardar el usuario actualizado
        return repository.save(existingUser);
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> findAll(Pageable page) {
        try {
            // Obtener todos los usuarios con paginación
            return repository.findAll(page).toList();
        } catch (Exception e) {
            throw new RuntimeException("Error al obtener la lista de usuarios", e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> findByDni(String dni, Pageable page) {
        try {
            // Buscar usuarios por DNI con paginación
            return repository.findByDniContaining(dni, page);
        } catch (Exception e) {
            throw new RuntimeException("Error al buscar usuarios por DNI", e);
        }
    }

    @Override
    public User updateAccountStatus(ObjectId id, boolean newState) {
        // TODO Auto-generated method stub
        User user = repository.findById(id).orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        // Actualizar el estado del usuario
        user.setState(newState);

        // Guardar el usuario con el nuevo estado
        return repository.save(user);
    }
}
