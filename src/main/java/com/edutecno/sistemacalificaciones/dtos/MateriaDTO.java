package com.edutecno.sistemacalificaciones.dtos;

public class MateriaDTO {

    private String nombre;

    public MateriaDTO() {}

    public MateriaDTO(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
}
