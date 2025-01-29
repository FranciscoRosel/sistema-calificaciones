package com.edutecno.sistemacalificaciones.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MateriaDTO {
    private String nombre;
    private Set<String> alumnos; // RUTs de los alumnos asociados
}