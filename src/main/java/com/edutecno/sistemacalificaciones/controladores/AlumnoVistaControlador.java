package com.edutecno.sistemacalificaciones.controladores;

import com.edutecno.sistemacalificaciones.dtos.AlumnoDTO;
import com.edutecno.sistemacalificaciones.servicios.AlumnoServicio;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/alumnos")
public class AlumnoVistaControlador {

    private final AlumnoServicio alumnoServicio;

    public AlumnoVistaControlador(AlumnoServicio alumnoServicio) {
        this.alumnoServicio = alumnoServicio;
    }

    @GetMapping
    public String listarAlumnos(Model modelo) {
        modelo.addAttribute("alumnos", alumnoServicio.listarAlumnos());
        return "lista-alumnos";
    }

    @GetMapping("/{rut}")
    public String obtenerAlumno(@PathVariable String rut, Model modelo) {
        AlumnoDTO alumno = alumnoServicio.obtenerAlumnoPorRut(rut);
        if (alumno == null) {
            return "error";
        }
        modelo.addAttribute("alumno", alumno);
        return "detalle-alumno";
    }

    @GetMapping("/{rut}/materias")
    public String mostrarFormularioAgregarMateria(@PathVariable String rut, Model modelo) {
        AlumnoDTO alumno = alumnoServicio.obtenerAlumnoPorRut(rut);
        if (alumno == null) {
            return "error";
        }
        modelo.addAttribute("alumno", alumno);
        return "agregar-materia";
    }

    @PostMapping("/{rut}/materias/{materiaId}")
    public String agregarMateria(@PathVariable String rut, @PathVariable Long materiaId) {
        alumnoServicio.agregarMateriaAAlumno(rut, materiaId);
        return "redirect:/alumnos/" + rut;
    }
}
