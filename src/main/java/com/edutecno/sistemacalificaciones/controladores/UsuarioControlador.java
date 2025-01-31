package com.edutecno.sistemacalificaciones.controladores;

import com.edutecno.sistemacalificaciones.modelos.Usuario;
import com.edutecno.sistemacalificaciones.seguridad.ProveedorTokenJWT;
import com.edutecno.sistemacalificaciones.servicios.UsuarioServicio;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioControlador {

    private final UsuarioServicio usuarioServicio;
    private final ProveedorTokenJWT proveedorTokenJWT;
    private final PasswordEncoder passwordEncoder;

    public UsuarioControlador(UsuarioServicio usuarioServicio, ProveedorTokenJWT proveedorTokenJWT, PasswordEncoder passwordEncoder) {
        this.usuarioServicio = usuarioServicio;
        this.proveedorTokenJWT = proveedorTokenJWT;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/signin")
    public ResponseEntity<?> iniciarSesion(@RequestBody Map<String, String> credenciales) {
        String nombreUsuario = credenciales.get("nombreUsuario");
        String contrasena = credenciales.get("contrasena");

        Optional<Usuario> usuarioOpt = usuarioServicio.buscarPorNombreUsuario(nombreUsuario);
        if (usuarioOpt.isEmpty()) {
            return ResponseEntity.status(401).body(Map.of("error", "Usuario no encontrado"));
        }

        Usuario usuario = usuarioOpt.get();

        if (!passwordEncoder.matches(contrasena, usuario.getContrasena())) {
            return ResponseEntity.status(401).body(Map.of("error", "Contraseña incorrecta"));
        }

        String token = proveedorTokenJWT.generarToken(usuario.getNombreUsuario(), usuario.getCorreo(), usuario.getRol().name());

        return ResponseEntity.ok(Map.of("token", token));
    }
}
