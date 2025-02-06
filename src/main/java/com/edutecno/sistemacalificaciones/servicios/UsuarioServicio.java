package com.edutecno.sistemacalificaciones.servicios;

import com.edutecno.sistemacalificaciones.dtos.UsuarioDTO;
import com.edutecno.sistemacalificaciones.modelos.Usuario;
import com.edutecno.sistemacalificaciones.repositorios.UsuarioRepositorio;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;
import java.util.Optional;

@Service
public class UsuarioServicio {

    private final UsuarioRepositorio usuarioRepositorio;
    private final RestTemplate restTemplate;
    private final String URL_LOGIN = "http://localhost:8080/api/usuarios/signin";

    public UsuarioServicio(UsuarioRepositorio usuarioRepositorio, RestTemplate restTemplate) {
        this.usuarioRepositorio = usuarioRepositorio;
        this.restTemplate = restTemplate;
    }

    public Optional<Usuario> buscarPorNombreUsuario(String nombreUsuario) {
        return usuarioRepositorio.findByNombreUsuario(nombreUsuario);
    }

    public String iniciarSesion(UsuarioDTO usuarioDTO) {
        try {
            ResponseEntity<Map> respuesta = restTemplate.exchange(
                    URL_LOGIN,
                    HttpMethod.POST,
                    new HttpEntity<>(usuarioDTO, crearHeaders()),
                    Map.class
            );

            if (respuesta.getStatusCode() == HttpStatus.OK && respuesta.getBody() != null) {
                return (String) respuesta.getBody().get("token");
            }
        } catch (Exception e) {
            System.out.println("Error en autenticación: " + e.getMessage());
        }
        return null;
    }

    private HttpHeaders crearHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return headers;
    }
}
