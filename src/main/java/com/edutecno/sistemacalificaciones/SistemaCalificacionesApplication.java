package com.edutecno.sistemacalificaciones;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.edutecno.sistemacalificaciones") // 🔥 Se asegura de escanear todos los paquetes
public class SistemaCalificacionesApplication {
    public static void main(String[] args) {
        SpringApplication.run(SistemaCalificacionesApplication.class, args);
    }
}
