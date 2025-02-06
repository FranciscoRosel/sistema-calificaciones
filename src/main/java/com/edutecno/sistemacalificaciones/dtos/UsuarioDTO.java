package com.edutecno.sistemacalificaciones.dtos;

import java.util.List;

public class UsuarioDTO {
    private String nombreUsuario;
    private String contrasena;
    private List<String> roles;

    public String getNombreUsuario() { return nombreUsuario; }
    public void setNombreUsuario(String nombreUsuario) { this.nombreUsuario = nombreUsuario; }

    public String getContrasena() { return contrasena; }
    public void setContrasena(String contrasena) { this.contrasena = contrasena; }

    public List<String> getRoles() { return roles; }
    public void setRoles(List<String> roles) { this.roles = roles; }
}
