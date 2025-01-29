package com.edutecno.sistemacalificaciones.servicios;

import com.edutecno.sistemacalificaciones.excepciones.RecursoNoEncontradoException;
import com.edutecno.sistemacalificaciones.modelos.Materia;
import com.edutecno.sistemacalificaciones.repositorios.MateriaRepositorio;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class MateriaServicio {

    private final MateriaRepositorio materiaRepositorio;
    private static final Logger logger = LoggerFactory.getLogger(MateriaServicio.class);

    public MateriaServicio(MateriaRepositorio materiaRepositorio) {
        this.materiaRepositorio = materiaRepositorio;
    }

    public Materia crearMateria(Materia materia) {
        logger.info("Creando nueva materia: {}", materia.getNombre());
        return materiaRepositorio.save(materia);
    }

    public List<Materia> listarMaterias() { // 🔴 CAMBIO AQUÍ (antes era listarMaterias)
        logger.info("Listando todas las materias...");
        return materiaRepositorio.findAll();
    }

    public Materia obtenerMateriaPorId(Long id) {
        return materiaRepositorio.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("No se encontró la materia con ID: " + id));
    }

    public Materia actualizarMateria(Long id, Materia materiaNueva) {
        Materia materia = materiaRepositorio.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("No se encontró la materia con ID: " + id));

        materia.setNombre(materiaNueva.getNombre());
        return materiaRepositorio.save(materia);
    }

    public void eliminarMateria(Long id) {
        logger.info("Eliminando materia con ID: {}", id);
        materiaRepositorio.deleteById(id);
        logger.info("Materia con ID: {} eliminada exitosamente", id);
    }
}
