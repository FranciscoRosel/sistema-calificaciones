package com.edutecno.sistemacalificaciones.seguridad;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class ProveedorTokenJWT {

    private static final Key CLAVE_SECRETA = Keys.secretKeyFor(SignatureAlgorithm.HS512);
    private static final long TIEMPO_EXPIRACION = 3600000; // 1 hora

    public String generarToken(String nombreUsuario, String correo, String rol) {
        return Jwts.builder()
                .setSubject(nombreUsuario)
                .claim("correo", correo)
                .claim("rol", rol)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + TIEMPO_EXPIRACION))
                .signWith(CLAVE_SECRETA)
                .compact();
    }

    public boolean validarToken(String token) {
        try {
            obtenerClaims(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public String obtenerNombreUsuario(String token) {
        return obtenerClaims(token).getSubject();
    }

    public Claims obtenerClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(CLAVE_SECRETA)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
