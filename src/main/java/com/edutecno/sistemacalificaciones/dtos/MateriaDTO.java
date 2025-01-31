package com.edutecno.sistemacalificaciones.dtos;

public class MateriaDTO {

    private String nombre;

    // Constructor vacío
    public MateriaDTO() {}

    // Constructor con nombre
    public MateriaDTO(String nombre) {
        this.nombre = nombre;
    }

    // Getters y Setters
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
}
