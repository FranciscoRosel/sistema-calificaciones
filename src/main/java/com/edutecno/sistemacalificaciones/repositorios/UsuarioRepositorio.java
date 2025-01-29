package com.edutecno.sistemacalificaciones.repositorios;

import com.edutecno.sistemacalificaciones.modelos.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsuarioRepositorio extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByNombreUsuarioAndContrasena(String nombreUsuario, String contrasena);
    Optional<Usuario> findByNombreUsuario(String nombreUsuario);
}