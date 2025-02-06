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

    private AlumnoDTO convertirAAlumnoDTO(Alumno alumno) {
        AlumnoDTO dto = new AlumnoDTO();
        dto.setRut(alumno.getRut());
        dto.setNombre(alumno.getNombre());
        dto.setCorreo(alumno.getCorreo());
        dto.setDireccion(alumno.getDireccion());

        Set<String> nombresMaterias = alumno.getMaterias().stream()
                .map(Materia::getNombre)
                .collect(Collectors.toSet());

        dto.setMaterias(nombresMaterias);
        return dto;
    }

    public List<AlumnoDTO> listarAlumnos() {
        return alumnoRepositorio.findAll()
                .stream()
                .map(this::convertirAAlumnoDTO)
                .collect(Collectors.toList());
    }

    public AlumnoDTO obtenerAlumnoPorRut(String rut) {
        Optional<Alumno> alumno = alumnoRepositorio.findByRut(rut);
        return alumno.map(this::convertirAAlumnoDTO).orElse(null);
    }

    @Transactional
    public AlumnoDTO crearAlumno(AlumnoDTO alumnoDTO) {
        Alumno alumno = new Alumno();
        alumno.setRut(alumnoDTO.getRut());
        alumno.setNombre(alumnoDTO.getNombre());
        alumno.setCorreo(alumnoDTO.getCorreo());
        alumno.setDireccion(alumnoDTO.getDireccion());

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

    public AlumnoDTO actualizarAlumno(String rut, AlumnoDTO alumnoDTO) {
        Optional<Alumno> optionalAlumno = alumnoRepositorio.findByRut(rut);

        if (optionalAlumno.isPresent()) {
            Alumno alumno = optionalAlumno.get();
            alumno.setNombre(alumnoDTO.getNombre());
            alumno.setCorreo(alumnoDTO.getCorreo());
            alumno.setDireccion(alumnoDTO.getDireccion());

            Set<Materia> nuevasMaterias = new HashSet<>();
            for (String nombreMateria : alumnoDTO.getMaterias()) {
                Materia materia = materiaRepositorio.findByNombre(nombreMateria)
                        .orElseGet(() -> new Materia(nombreMateria));
                nuevasMaterias.add(materia);
            }
            alumno.setMaterias(nuevasMaterias);

            alumnoRepositorio.save(alumno);
            return convertirAAlumnoDTO(alumno);
        }

        return null;
    }

    public void eliminarAlumno(String rut) {
        Optional<Alumno> alumno = alumnoRepositorio.findByRut(rut);
        alumno.ifPresent(alumnoRepositorio::delete);
    }
}
