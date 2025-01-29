package com.edutecno.sistemacalificaciones.controladores;

import com.edutecno.sistemacalificaciones.modelos.Materia;
import com.edutecno.sistemacalificaciones.servicios.MateriaServicio;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/materias")
public class MateriaControlador {

    private final MateriaServicio materiaServicio;
    private static final Logger logger = LoggerFactory.getLogger(MateriaControlador.class);

    public MateriaControlador(MateriaServicio materiaServicio) {
        this.materiaServicio = materiaServicio;
    }

    @PostMapping
    public ResponseEntity<Materia> crearMateria(@RequestBody Materia materia) {
        logger.info("Creando materia: {}", materia.getNombre());
        Materia nuevaMateria = materiaServicio.crearMateria(materia);
        logger.info("Materia creada exitosamente: {}", nuevaMateria.getNombre());
        return ResponseEntity.ok(nuevaMateria);
    }

    @GetMapping
    public ResponseEntity<List<Materia>> listarMaterias() {
        logger.info("Listando todas las materias");
        List<Materia> materias = materiaServicio.listarMaterias();
        logger.info("Cantidad de materias encontradas: {}", materias.size());
        return ResponseEntity.ok(materias);
    }
    // Obtener una sola materia por su ID
    @GetMapping("/{id}")
    public ResponseEntity<Materia> obtenerMateriaPorId(@PathVariable Long id) {
        Materia materia = materiaServicio.obtenerMateriaPorId(id);
        return ResponseEntity.ok(materia);
    }
    // Actualizar una materia por ID
    @PutMapping("/{id}")
    public ResponseEntity<Materia> actualizarMateria(@PathVariable Long id, @RequestBody Materia materia) {
        Materia materiaActualizada = materiaServicio.actualizarMateria(id, materia);
        return ResponseEntity.ok(materiaActualizada);
    }



    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarMateria(@PathVariable Long id) {
        logger.info("Eliminando materia con ID: {}", id);
        materiaServicio.eliminarMateria(id);
        logger.info("Materia eliminada exitosamente.");
        return ResponseEntity.ok(Map.of("mensaje", "Materia eliminada exitosamente"));
    }
}
