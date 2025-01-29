package com.edutecno.sistemacalificaciones.seguridad;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component  // 🔥 Esto registra el filtro como un bean en Spring
public class FiltroTokenJWT extends OncePerRequestFilter {

    private final ProveedorTokenJWT proveedorTokenJWT;

    public FiltroTokenJWT(ProveedorTokenJWT proveedorTokenJWT) {
        this.proveedorTokenJWT = proveedorTokenJWT;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String token = request.getHeader("Authorization");
        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7); // Remover "Bearer "
            if (proveedorTokenJWT.validarToken(token)) {
                String usuario = proveedorTokenJWT.obtenerNombreUsuario(token);
                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(usuario, null, null);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }
        filterChain.doFilter(request, response);
    }
}
