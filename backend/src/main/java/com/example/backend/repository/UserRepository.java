package com.example.backend.repository;

import com.example.backend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    //Buscar usuario por username
    Optional<User> findByUsername(String username);

    //Verificar si existe un usuario con un rol específico
    @Query("SELECT COUNT(u) > 0 FROM User u WHERE u.role = :role")
    boolean existsByRole(String role);

    Optional<User> findByDni(String dni);
}