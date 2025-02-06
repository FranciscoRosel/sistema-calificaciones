package com.edutecno.sistemacalificaciones.controladores;

import com.edutecno.sistemacalificaciones.dtos.UsuarioDTO;
import com.edutecno.sistemacalificaciones.servicios.UsuarioServicio;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/login")
public class LoginControlador {
    private final UsuarioServicio usuarioServicio;

    public LoginControlador(UsuarioServicio usuarioServicio) {
        this.usuarioServicio = usuarioServicio;
    }

    @GetMapping
    public String mostrarFormularioLogin() {
        return "login";
    }

    @PostMapping
    public String procesarLogin(@ModelAttribute UsuarioDTO usuarioDTO, HttpSession session, Model model) {
        String token = usuarioServicio.iniciarSesion(usuarioDTO);

        if (token != null) {
            session.setAttribute("token", token);
            return "redirect:/home";
        } else {
            model.addAttribute("error", "Credenciales incorrectas");
            return "login";
        }
    }
}
