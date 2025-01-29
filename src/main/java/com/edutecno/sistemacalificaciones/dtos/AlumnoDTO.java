package com.edutecno.sistemacalificaciones.dtos;

import java.util.Set;

public class AlumnoDTO {
    private String rut;
    private String nombre;
    private String direccion;
    private Set<String> materias; // Nombres de las materias

    // Getters y setters
    public String getRut() {
        return rut;
    }

    public void setRut(String rut) {
        this.rut = rut;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public Set<String> getMaterias() {
        return materias;
    }

    public void setMaterias(Set<String> materias) {
        this.materias = materias;
    }
}
