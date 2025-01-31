package com.edutecno.sistemacalificaciones.modelos;

import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "alumnos")
public class Alumno {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // OBLIGATORIO PARA AUTO_INCREMENT
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;

    @Column(unique = true, nullable = false)
    private String rut;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false, unique = true)
    private String correo;

    @ManyToMany
    @JoinTable(
            name = "alumno_materia",
            joinColumns = @JoinColumn(name = "alumno_id"),
            inverseJoinColumns = @JoinColumn(name = "materia_id")
    )
    private Set<Materia> materias = new HashSet<>();

    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getRut() { return rut; }
    public void setRut(String rut) { this.rut = rut; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getCorreo() { return correo; }
    public void setCorreo(String correo) { this.correo = correo; }

    public Set<Materia> getMaterias() { return materias; }
    public void setMaterias(Set<Materia> materias) { this.materias = materias; }
}
