package com.edutecno.sistemacalificaciones.repositorios;

import com.edutecno.sistemacalificaciones.modelos.Materia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MateriaRepositorio extends JpaRepository<Materia, Long> {

    // Buscar materia por nombre
    Optional<Materia> findByNombre(String nombre);
}
