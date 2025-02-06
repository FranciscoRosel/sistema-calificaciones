package com.edutecno.sistemacalificaciones.controladores;

import com.edutecno.sistemacalificaciones.servicios.AlumnoServicio;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/home")
public class HomeControlador {
    private final AlumnoServicio alumnoServicio;

    public HomeControlador(AlumnoServicio alumnoServicio) {
        this.alumnoServicio = alumnoServicio;
    }

    @GetMapping
    public String mostrarPaginaPrincipal(HttpSession session, Model model) {
        if (session.getAttribute("token") == null) {
            return "redirect:/login";
        }
        model.addAttribute("alumnos", alumnoServicio.listarAlumnos());
        return "home";
    }
}
