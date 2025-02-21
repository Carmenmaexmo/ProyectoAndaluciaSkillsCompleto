package com.example.backend.repository;

import com.example.backend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    //Buscar usuario por username
    Optional<User> findByUsername(String username);

    //Obtener usuarios por DNI
    Optional<User> findByDni(String dni);

    List<User> findByRole(String rol);
}