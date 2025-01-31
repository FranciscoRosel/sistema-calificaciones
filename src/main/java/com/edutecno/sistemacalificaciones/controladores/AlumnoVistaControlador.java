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

    // 🔹 Página para listar alumnos
    @GetMapping
    public String listarAlumnos(Model modelo) {
        modelo.addAttribute("alumnos", alumnoServicio.listarAlumnos());
        return "lista-alumnos"; // Asegúrate de tener esta vista en templates
    }

    // 🔹 Obtener detalles de un alumno por su RUT
    @GetMapping("/{rut}")
    public String obtenerAlumno(@PathVariable String rut, Model modelo) {
        AlumnoDTO alumno = alumnoServicio.obtenerAlumnoPorRut(rut);
        if (alumno == null) {
            return "error"; // Redirigir a una vista de error si el alumno no existe
        }
        modelo.addAttribute("alumno", alumno);
        return "detalle-alumno"; // Asegúrate de tener esta vista en templates
    }

    // 🔹 Mostrar formulario para agregar una materia a un alumno
    @GetMapping("/{rut}/materias")
    public String mostrarFormularioAgregarMateria(@PathVariable String rut, Model modelo) {
        AlumnoDTO alumno = alumnoServicio.obtenerAlumnoPorRut(rut);
        if (alumno == null) {
            return "error";
        }
        modelo.addAttribute("alumno", alumno);
        return "agregar-materia"; // Vista para seleccionar y asignar materias
    }

    // 🔹 Agregar materia a un alumno
    @PostMapping("/{rut}/materias/{materiaId}")
    public String agregarMateria(@PathVariable String rut, @PathVariable Long materiaId) {
        alumnoServicio.agregarMateriaAAlumno(rut, materiaId);
        return "redirect:/alumnos/" + rut;
    }
}
