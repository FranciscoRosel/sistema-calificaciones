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

@Component
public class FiltroTokenJWT extends OncePerRequestFilter {

    private final ProveedorTokenJWT proveedorTokenJWT;

    public FiltroTokenJWT(ProveedorTokenJWT proveedorTokenJWT) {
        this.proveedorTokenJWT = proveedorTokenJWT;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String ruta = request.getRequestURI();
        if (ruta.contains("/api/usuarios/signin") || ruta.contains("/api/auth/buscar")) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = request.getHeader("Authorization");
        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7);
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
