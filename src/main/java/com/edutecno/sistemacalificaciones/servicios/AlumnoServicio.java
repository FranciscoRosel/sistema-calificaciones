package com.edutecno.sistemacalificaciones.servicios;

import com.edutecno.sistemacalificaciones.dtos.AlumnoDTO;
import com.edutecno.sistemacalificaciones.modelos.Alumno;
import com.edutecno.sistemacalificaciones.modelos.Materia;
import com.edutecno.sistemacalificaciones.repositorios.AlumnoRepositorio;
import com.edutecno.sistemacalificaciones.repositorios.MateriaRepositorio;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AlumnoServicio {

    private final AlumnoRepositorio alumnoRepositorio;
    private final MateriaRepositorio materiaRepositorio;

    public AlumnoServicio(AlumnoRepositorio alumnoRepositorio, MateriaRepositorio materiaRepositorio) {
        this.alumnoRepositorio = alumnoRepositorio;
        this.materiaRepositorio = materiaRepositorio;
    }

    // 🔹 Convertir Alumno a AlumnoDTO
    private AlumnoDTO convertirAAlumnoDTO(Alumno alumno) {
        AlumnoDTO dto = new AlumnoDTO();
        dto.setRut(alumno.getRut());
        dto.setNombre(alumno.getNombre());
        dto.setCorreo(alumno.getCorreo());

        // 🔹 Convertir materias a nombres
        Set<String> nombresMaterias = alumno.getMaterias().stream()
                .map(Materia::getNombre)
                .collect(Collectors.toSet());

        dto.setMaterias(nombresMaterias);
        return dto;
    }

    // 🔹 Listar alumnos
    public List<AlumnoDTO> listarAlumnos() {
        return alumnoRepositorio.findAll()
                .stream()
                .map(this::convertirAAlumnoDTO)
                .collect(Collectors.toList());
    }

    // 🔹 Obtener un alumno por su RUT
    public AlumnoDTO obtenerAlumnoPorRut(String rut) {
        Optional<Alumno> alumno = alumnoRepositorio.findByRut(rut);
        return alumno.map(this::convertirAAlumnoDTO).orElse(null);
    }

    // 🔹 Crear un nuevo alumno con materias por nombre
    @Transactional
    public AlumnoDTO crearAlumno(AlumnoDTO alumnoDTO) {
        Alumno alumno = new Alumno();
        alumno.setRut(alumnoDTO.getRut());
        alumno.setNombre(alumnoDTO.getNombre());
        alumno.setCorreo(alumnoDTO.getCorreo());

        // 🔹 Agregar materias solo con nombres
        if (alumnoDTO.getMaterias() != null) {
            Set<Materia> materias = new HashSet<>();
            for (String nombreMateria : alumnoDTO.getMaterias()) {
                Materia materia = materiaRepositorio.findByNombre(nombreMateria)
                        .orElseGet(() -> materiaRepositorio.save(new Materia(nombreMateria)));
                materias.add(materia);
            }
            alumno.setMaterias(materias);
        }

        alumno = alumnoRepositorio.save(alumno);
        return convertirAAlumnoDTO(alumno);
    }

    // 🔹 Agregar materia a un alumno
    @Transactional
    public void agregarMateriaAAlumno(String rut, Long materiaId) {
        Optional<Alumno> alumnoOpt = alumnoRepositorio.findByRut(rut);
        Optional<Materia> materiaOpt = materiaRepositorio.findById(materiaId);

        if (alumnoOpt.isPresent() && materiaOpt.isPresent()) {
            Alumno alumno = alumnoOpt.get();
            Materia materia = materiaOpt.get();
            alumno.getMaterias().add(materia);
            alumnoRepositorio.save(alumno);
        }
    }

    // 🔹 Eliminar un alumno
    public void eliminarAlumno(String rut) {
        Optional<Alumno> alumno = alumnoRepositorio.findByRut(rut);
        alumno.ifPresent(alumnoRepositorio::delete);
    }
}
