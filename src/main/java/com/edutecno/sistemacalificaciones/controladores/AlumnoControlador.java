package com.edutecno.sistemacalificaciones.controladores;

import com.edutecno.sistemacalificaciones.dtos.AlumnoDTO;
import com.edutecno.sistemacalificaciones.servicios.AlumnoServicio;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/alumnos")
public class AlumnoControlador {

    private final AlumnoServicio alumnoServicio;

    public AlumnoControlador(AlumnoServicio alumnoServicio) {
        this.alumnoServicio = alumnoServicio;
    }

    // 🔹 Crear un nuevo alumno (Solo ADMIN)
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<AlumnoDTO> crearAlumno(@RequestBody AlumnoDTO alumnoDTO) {
        return ResponseEntity.ok(alumnoServicio.crearAlumno(alumnoDTO));
    }

    // 🔹 Listar todos los alumnos (Cualquier usuario autenticado)
    @PreAuthorize("isAuthenticated()")
    @GetMapping
    public ResponseEntity<List<AlumnoDTO>> listarAlumnos() {
        return ResponseEntity.ok(alumnoServicio.listarAlumnos());
    }

    // 🔹 Obtener un solo alumno por su RUT (Cualquier usuario autenticado)
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/{rut}")
    public ResponseEntity<AlumnoDTO> obtenerAlumnoPorRut(@PathVariable String rut) {
        AlumnoDTO alumno = alumnoServicio.obtenerAlumnoPorRut(rut);
        return alumno != null ? ResponseEntity.ok(alumno) : ResponseEntity.notFound().build();
    }

    // 🔹 Eliminar un alumno por su RUT (Solo ADMIN)
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{rut}")
    public ResponseEntity<String> eliminarAlumno(@PathVariable String rut) {
        alumnoServicio.eliminarAlumno(rut);
        return ResponseEntity.ok("Alumno eliminado exitosamente");
    }
}
