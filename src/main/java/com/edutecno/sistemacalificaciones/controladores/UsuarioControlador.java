package com.edutecno.sistemacalificaciones.controladores;

import com.edutecno.sistemacalificaciones.modelos.Usuario;
import com.edutecno.sistemacalificaciones.servicios.UsuarioServicio;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.Optional;

@Controller
public class UsuarioControlador {

    private final UsuarioServicio usuarioServicio;

    public UsuarioControlador(UsuarioServicio usuarioServicio) {
        this.usuarioServicio = usuarioServicio;
    }

    @GetMapping("/login")
    public String mostrarLogin() {
        return "login"; // Renderiza login.html
    }

    @GetMapping("/home")
    public String mostrarHome(Model model, HttpSession session) {
        // 🔥 Obtener usuario autenticado con Spring Security
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String nombreUsuario = auth.getName();

        Optional<Usuario> usuarioOpt = usuarioServicio.buscarPorNombreUsuario(nombreUsuario);
        if (usuarioOpt.isEmpty()) {
            return "redirect:/login";
        }

        Usuario usuario = usuarioOpt.get();
        session.setAttribute("usuario", usuario);

        // 🔥 Cargar alumnos y materias desde el servicio
        List<?> alumnos = usuarioServicio.obtenerAlumnos();
        List<?> materias = usuarioServicio.obtenerMaterias();

        // 🔥 Enviar datos a la vista
        model.addAttribute("alumnos", alumnos);
        model.addAttribute("materias", materias);

        return "home";
    }

    @GetMapping("/logout")
    public String cerrarSesion(HttpSession session) {
        session.invalidate();
        return "redirect:/login?logout";
    }
}
