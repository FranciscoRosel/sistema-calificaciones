package com.edutecno.sistemacalificaciones.servicios;

import com.edutecno.sistemacalificaciones.dtos.AlumnoDTO;
import com.edutecno.sistemacalificaciones.excepciones.RecursoNoEncontradoException;
import com.edutecno.sistemacalificaciones.modelos.Alumno;
import com.edutecno.sistemacalificaciones.modelos.Materia;
import com.edutecno.sistemacalificaciones.repositorios.AlumnoRepositorio;
import com.edutecno.sistemacalificaciones.repositorios.MateriaRepositorio;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AlumnoServicio {

    private final AlumnoRepositorio alumnoRepositorio;
    private final MateriaRepositorio materiaRepositorio;
    private static final Logger logger = LoggerFactory.getLogger(AlumnoServicio.class);

    public AlumnoServicio(AlumnoRepositorio alumnoRepositorio, MateriaRepositorio materiaRepositorio) {
        this.alumnoRepositorio = alumnoRepositorio;
        this.materiaRepositorio = materiaRepositorio;
    }

    public AlumnoDTO crearAlumno(AlumnoDTO alumnoDTO) {
        logger.info("Creando alumno con RUT: {}", alumnoDTO.getRut());
        Alumno alumno = new Alumno();
        alumno.setRut(alumnoDTO.getRut());
        alumno.setNombre(alumnoDTO.getNombre());
        alumno.setDireccion(alumnoDTO.getDireccion());

        if (alumnoDTO.getMaterias() != null) {
            Set<Materia> materias = alumnoDTO.getMaterias().stream()
                    .map(nombreMateria -> materiaRepositorio.findByNombre(nombreMateria)
                            .orElseThrow(() -> new RecursoNoEncontradoException("Materia no encontrada: " + nombreMateria)))
                    .collect(Collectors.toSet());
            alumno.setListaMaterias(materias);
        }

        Alumno alumnoGuardado = alumnoRepositorio.save(alumno);
        return convertirADTO(alumnoGuardado);
    }

    public List<AlumnoDTO> listarAlumnos() {
        logger.info("Listando todos los alumnos...");
        return alumnoRepositorio.findAll().stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    public AlumnoDTO obtenerAlumnoPorRut(String rut) {
        Alumno alumno = alumnoRepositorio.findByRut(rut)
                .orElseThrow(() -> new RecursoNoEncontradoException("No se encontró el alumno con RUT: " + rut));
        return convertirADTO(alumno);
    }

    public AlumnoDTO actualizarAlumno(String rut, AlumnoDTO alumnoDTO) {
        Alumno alumno = alumnoRepositorio.findByRut(rut)
                .orElseThrow(() -> new RecursoNoEncontradoException("No se encontró el alumno con RUT: " + rut));

        alumno.setNombre(alumnoDTO.getNombre());
        alumno.setDireccion(alumnoDTO.getDireccion());

        if (alumnoDTO.getMaterias() != null) {
            Set<Materia> materias = alumnoDTO.getMaterias().stream()
                    .map(nombreMateria -> materiaRepositorio.findByNombre(nombreMateria)
                            .orElseThrow(() -> new RecursoNoEncontradoException("Materia no encontrada: " + nombreMateria)))
                    .collect(Collectors.toSet());
            alumno.setListaMaterias(materias);
        }

        Alumno alumnoGuardado = alumnoRepositorio.save(alumno);
        return convertirADTO(alumnoGuardado);
    }

    public void agregarMateriaAAlumno(String rut, Long materiaId) {
        Alumno alumno = alumnoRepositorio.findByRut(rut)
                .orElseThrow(() -> new RecursoNoEncontradoException("Alumno no encontrado con RUT: " + rut));

        Materia materia = materiaRepositorio.findById(materiaId)
                .orElseThrow(() -> new RecursoNoEncontradoException("Materia no encontrada con ID: " + materiaId));

        alumno.getListaMaterias().add(materia);
        alumnoRepositorio.save(alumno);
    }

    public void eliminarAlumno(String rut) {
        logger.info("Eliminando alumno con RUT: {}", rut);
        Alumno alumno = alumnoRepositorio.findByRut(rut)
                .orElseThrow(() -> new RecursoNoEncontradoException("No se encontró el alumno con RUT: " + rut));
        alumnoRepositorio.delete(alumno);
        logger.info("Alumno con RUT: {} eliminado exitosamente", rut);
    }

    private AlumnoDTO convertirADTO(Alumno alumno) {
        AlumnoDTO dto = new AlumnoDTO();
        dto.setRut(alumno.getRut());
        dto.setNombre(alumno.getNombre());
        dto.setDireccion(alumno.getDireccion());
        dto.setMaterias(alumno.getListaMaterias().stream()
                .map(Materia::getNombre)
                .collect(Collectors.toSet()));
        return dto;
    }
}
