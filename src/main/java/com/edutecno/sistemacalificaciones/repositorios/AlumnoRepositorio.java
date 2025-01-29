package com.edutecno.sistemacalificaciones.repositorios;

import com.edutecno.sistemacalificaciones.modelos.Alumno;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AlumnoRepositorio extends JpaRepository<Alumno, String> {
    Optional<Alumno> findByRut(String rut);
}
