package com.edutecno.sistemacalificaciones.repositorios;

import com.edutecno.sistemacalificaciones.modelos.Materia;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MateriaRepositorio extends JpaRepository<Materia, Long> {
    Optional<Materia> findByNombre(String nombre);
}
