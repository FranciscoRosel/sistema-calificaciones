package com.edutecno.sistemacalificaciones.modelos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "materias")
public class Materia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String nombre;

    @ManyToMany(mappedBy = "materias", fetch = FetchType.LAZY)
    @JsonIgnore // 🔹 Evita la recursión infinita al serializar
    private Set<Alumno> alumnos = new HashSet<>();

    // 🔹 Constructor vacío (obligatorio para JPA)
    public Materia() {}

    // 🔹 Constructor con nombre
    public Materia(String nombre) {
        this.nombre = nombre;
    }

    // 🔹 Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public Set<Alumno> getAlumnos() { return alumnos; }
    public void setAlumnos(Set<Alumno> alumnos) { this.alumnos = alumnos; }
}
