package com.example.backend.service;

import com.example.backend.dto.UserDTO;
import com.example.backend.dto.UserRegisterDTO;
import com.example.backend.mapper.UserMapper;
import com.example.backend.model.Especialidad;
import com.example.backend.model.User;
import com.example.backend.repository.UserRepository;
import com.example.backend.repository.EspecialidadRepository;
import com.example.backend.service.base.UserServiceBase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@Service
public class UserService implements UserServiceBase, UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EspecialidadRepository especialidadRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserMapper userMapper;

    private static final List<String> VALID_ROLES = List.of("EXPERTO", "ADMIN");

    @Override
    public List<UserDTO> obtenerTodos() {
        return userMapper.toDTOs(userRepository.findAll());
    }

    @Override
    public Optional<UserDTO> obtenerPorId(Integer id) {
        return userRepository.findById(id).map(userMapper::toDTO);
    }

    @Override
    public UserDTO agregarUser(UserRegisterDTO userRegisterDTO) {
        // Verificar si el rol es válido
        if (!VALID_ROLES.contains(userRegisterDTO.getRole().toUpperCase())) {
            throw new IllegalArgumentException("Rol no válido");
        }

        Especialidad especialidad = especialidadRepository.findById(userRegisterDTO.getEspecialidadId())
                .orElseThrow(() -> new RuntimeException("Especialidad no encontrada"));

        // Convertir DTO a entidad
        User user = userMapper.toEntity(userRegisterDTO);
        user.setPassword(passwordEncoder.encode(user.getPassword())); // Encripta la contraseña
        user.setEspecialidad(especialidad); // Asignar especialidad al usuario

        // Guardar el usuario
        user = userRepository.save(user);

        return userMapper.toDTO(user);
    }

    @Override
    public UserDTO actualizarUser(Integer id, UserRegisterDTO userRegisterDTO) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        // Actualizar los campos del usuario
        user.setUsername(userRegisterDTO.getUsername());
        user.setNombre(userRegisterDTO.getNombre());
        user.setApellidos(userRegisterDTO.getApellidos());
        user.setDni(userRegisterDTO.getDni());
        user.setRole(userRegisterDTO.getRole());

        Especialidad especialidad = especialidadRepository.findById(userRegisterDTO.getEspecialidadId())
                .orElseThrow(() -> new RuntimeException("Especialidad no encontrada"));
        user.setEspecialidad(especialidad);

        // Guardar los cambios
        user = userRepository.save(user);

        return userMapper.toDTO(user);
    }

    @Override
    public void eliminarUser(Integer id) {
        userRepository.deleteById(id);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), new ArrayList<>());
    }

    public String getUserRole(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));
        return user.getRole();
    }

    // Obtener el id de la especialidad del usuario
    public Integer getUserEspecialidadId(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));
        return user.getEspecialidad().getIdEspecialidad();
       
    }

    public List<UserDTO> obtenerUsuariosPorRol(String rol) {
        return userMapper.toDTOs(userRepository.findByRole(rol));
    }
}