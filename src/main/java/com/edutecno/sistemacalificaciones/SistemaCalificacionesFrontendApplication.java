package com.edutecno.sistemacalificaciones;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@SpringBootApplication
public class SistemaCalificacionesFrontendApplication {
    public static void main(String[] args) {
        SpringApplication.run(SistemaCalificacionesFrontendApplication.class, args);
    }
}

@Controller
class VistaControlador {
    @GetMapping("/")
    public String mostrarPaginaInicio(Model modelo) {
        modelo.addAttribute("mensaje", "Bienvenido al Sistema de Calificaciones");
        return "inicio";
    }

    @GetMapping("/alumnos")
    public String listarAlumnos(Model modelo) {
        // Simulación de datos (reemplazar con servicio real)
        List<String> alumnos = List.of("Juan Pérez", "María González", "Carlos López");
        modelo.addAttribute("alumnos", alumnos);
        return "alumnos";
    }

    @GetMapping("/materias")
    public String listarMaterias(Model modelo) {
        // Simulación de datos (reemplazar con servicio real)
        List<String> materias = List.of("Matemáticas", "Historia", "Ciencias");
        modelo.addAttribute("materias", materias);
        return "materias";
    }

    @GetMapping("/detalle-alumno")
    public String detalleAlumno(@RequestParam String nombre, Model modelo) {
        modelo.addAttribute("nombre", nombre);
        modelo.addAttribute("detalle", "Información detallada sobre " + nombre);
        modelo.addAttribute("notas", List.of(5.0, 6.0, 4.5));
        modelo.addAttribute("comentarios", List.of("Buen desempeño", "Debe mejorar en matemáticas", "Excelente actitud"));
        modelo.addAttribute("asistencia", "90%");
        return "detalle-alumno";
    }
}
