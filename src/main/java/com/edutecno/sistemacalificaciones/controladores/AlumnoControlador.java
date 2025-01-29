package com.edutecno.sistemacalificaciones.controladores;

import com.edutecno.sistemacalificaciones.dtos.AlumnoDTO;
import com.edutecno.sistemacalificaciones.servicios.AlumnoServicio;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/alumnos")
public class AlumnoControlador {

    private final AlumnoServicio alumnoServicio;
    private static final Logger logger = LoggerFactory.getLogger(AlumnoControlador.class);

    public AlumnoControlador(AlumnoServicio alumnoServicio) {
        this.alumnoServicio = alumnoServicio;
    }

    // Crear un nuevo alumno
    @PostMapping
    public ResponseEntity<AlumnoDTO> crearAlumno(@RequestBody AlumnoDTO alumnoDTO) {
        logger.info("Creando alumno con rut: {}", alumnoDTO.getRut());
        AlumnoDTO alumnoGuardado = alumnoServicio.crearAlumno(alumnoDTO);
        logger.info("Alumno creado exitosamente: {}", alumnoGuardado.getNombre());
        return ResponseEntity.ok(alumnoGuardado);
    }

    // Listar todos los alumnos
    @GetMapping
    public ResponseEntity<List<AlumnoDTO>> listarAlumnos() {
        logger.info("Listando todos los alumnos");
        List<AlumnoDTO> listaAlumnos = alumnoServicio.listarAlumnos();
        logger.info("Cantidad de alumnos encontrados: {}", listaAlumnos.size());
        return ResponseEntity.ok(listaAlumnos);
    }

    // Obtener un solo alumno por su RUT
    @GetMapping("/{rut}")
    public ResponseEntity<AlumnoDTO> obtenerAlumnoPorRut(@PathVariable String rut) {
        logger.info("Obteniendo alumno con rut: {}", rut);
        AlumnoDTO alumno = alumnoServicio.obtenerAlumnoPorRut(rut);
        if (alumno == null) {
            logger.error("Alumno con rut {} no encontrado", rut);
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(alumno);
    }

    // Actualizar un alumno por su RUT
    @PutMapping("/{rut}")
    public ResponseEntity<AlumnoDTO> actualizarAlumno(@PathVariable String rut, @RequestBody AlumnoDTO alumnoDTO) {
        logger.info("Actualizando alumno con rut: {}", rut);
        AlumnoDTO alumnoActualizado = alumnoServicio.actualizarAlumno(rut, alumnoDTO);
        return ResponseEntity.ok(alumnoActualizado);
    }

    // Eliminar un alumno por su RUT
    @DeleteMapping("/{rut}")
    public ResponseEntity<?> eliminarAlumno(@PathVariable String rut) {
        logger.info("Eliminando alumno con rut: {}", rut);
        alumnoServicio.eliminarAlumno(rut);
        logger.info("Alumno eliminado exitosamente.");
        return ResponseEntity.ok().body("Alumno eliminado exitosamente");
    }
}
