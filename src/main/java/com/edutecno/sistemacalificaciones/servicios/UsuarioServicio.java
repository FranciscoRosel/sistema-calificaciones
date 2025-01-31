package com.edutecno.sistemacalificaciones.servicios;

import com.edutecno.sistemacalificaciones.modelos.Usuario;
import com.edutecno.sistemacalificaciones.repositorios.UsuarioRepositorio;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Service
public class UsuarioServicio {

    private final UsuarioRepositorio usuarioRepositorio;
    private final RestTemplate restTemplate;

    public UsuarioServicio(UsuarioRepositorio usuarioRepositorio, RestTemplate restTemplate) {
        this.usuarioRepositorio = usuarioRepositorio;
        this.restTemplate = restTemplate;
    }

    public Optional<Usuario> buscarPorNombreUsuario(String nombreUsuario) {
        return usuarioRepositorio.findByNombreUsuario(nombreUsuario);
    }
}
