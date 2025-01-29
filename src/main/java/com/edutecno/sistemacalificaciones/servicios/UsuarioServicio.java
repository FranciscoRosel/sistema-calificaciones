package com.edutecno.sistemacalificaciones.servicios;

import com.edutecno.sistemacalificaciones.excepciones.RecursoNoEncontradoException;
import com.edutecno.sistemacalificaciones.modelos.Alumno;
import com.edutecno.sistemacalificaciones.modelos.Materia;
import com.edutecno.sistemacalificaciones.modelos.Usuario;
import com.edutecno.sistemacalificaciones.repositorios.AlumnoRepositorio;
import com.edutecno.sistemacalificaciones.repositorios.MateriaRepositorio;
import com.edutecno.sistemacalificaciones.repositorios.UsuarioRepositorio;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioServicio {

    private final UsuarioRepositorio usuarioRepositorio;
    private final AlumnoRepositorio alumnoRepositorio;
    private final MateriaRepositorio materiaRepositorio;
    private final PasswordEncoder passwordEncoder;

    public UsuarioServicio(UsuarioRepositorio usuarioRepositorio, AlumnoRepositorio alumnoRepositorio, MateriaRepositorio materiaRepositorio, PasswordEncoder passwordEncoder) {
        this.usuarioRepositorio = usuarioRepositorio;
        this.alumnoRepositorio = alumnoRepositorio;
        this.materiaRepositorio = materiaRepositorio;
        this.passwordEncoder = passwordEncoder;
    }

    public Optional<Usuario> buscarPorNombreUsuario(String nombreUsuario) {
        return usuarioRepositorio.findByNombreUsuario(nombreUsuario);
    }

    public boolean validarCredenciales(String nombreUsuario, String contrasena) {
        Usuario usuario = usuarioRepositorio.findByNombreUsuario(nombreUsuario)
                .orElseThrow(() -> new RecursoNoEncontradoException("Usuario no encontrado con nombre de usuario: " + nombreUsuario));

        // 🔥 Comparamos la contraseña ingresada con la cifrada en la BD usando BCrypt
        return passwordEncoder.matches(contrasena, usuario.getContrasena());
    }

    public List<Alumno> obtenerAlumnos() {
        return alumnoRepositorio.findAll();
    }

    public List<Materia> obtenerMaterias() {
        return materiaRepositorio.findAll();
    }
}
