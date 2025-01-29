package com.edutecno.sistemacalificaciones.controladores;

import com.edutecno.sistemacalificaciones.dtos.AlumnoDTO;
import com.edutecno.sistemacalificaciones.modelos.Materia;
import com.edutecno.sistemacalificaciones.servicios.AlumnoServicio;
import com.edutecno.sistemacalificaciones.servicios.MateriaServicio;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class AlumnoVistaControlador {

    private final AlumnoServicio alumnoServicio;
    private final MateriaServicio materiaServicio;

    public AlumnoVistaControlador(AlumnoServicio alumnoServicio, MateriaServicio materiaServicio) {
        this.alumnoServicio = alumnoServicio;
        this.materiaServicio = materiaServicio;
    }

    @GetMapping("/alumnos/detalle/{rut}")
    public String verDetalleAlumno(@PathVariable String rut, Model model) {
        // 🔥 Verificar autenticación
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated()) {
            return "redirect:/login";
        }

        AlumnoDTO alumno = alumnoServicio.obtenerAlumnoPorRut(rut);
        if (alumno == null) {
            return "error";
        }

        List<Materia> todasLasMaterias = materiaServicio.listarMaterias();
        List<Materia> materiasDisponibles = todasLasMaterias.stream()
                .filter(m -> !alumno.getMaterias().contains(m.getNombre()))
                .toList();

        model.addAttribute("alumno", alumno);
        model.addAttribute("materias", materiasDisponibles);
        return "detalle-alumno";
    }

    @PostMapping("/alumnos/detalle/{rut}/agregar-materia")
    public String agregarMateria(@PathVariable String rut, @RequestParam Long materiaId) {
        if (materiaId == null) {
            return "redirect:/alumnos/detalle/" + rut;
        }

        alumnoServicio.agregarMateriaAAlumno(rut, materiaId);

        return "redirect:/alumnos/detalle/" + rut;
    }
}
