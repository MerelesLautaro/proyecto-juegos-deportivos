package com.lautadev.juegos_deportivos.config.filter;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.lautadev.juegos_deportivos.util.JWTUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collection;

public class JWTTokenValidator extends OncePerRequestFilter {
    //OncePerRequestFilter = Filtro que se ejecuta por cada request
    private JWTUtils jwtUtils;

    public JWTTokenValidator(JWTUtils jwtUtils){
        this.jwtUtils = jwtUtils;
    }


    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) throws ServletException, IOException {

        String jwtToken = request.getHeader(HttpHeaders.AUTHORIZATION);

        if(jwtToken!=null){
            //Bearer tipo de tokenizacion -> substring nos elimina la palabra 'Bearer' para comparar el token correctamente
            jwtToken = jwtToken.substring(7);

            //decodificamos
            DecodedJWT decodedJWT = jwtUtils.validateToken(jwtToken);

            //traemos el usuario
            String username = jwtUtils.extractUsername(decodedJWT);

            //traemos los permisos seperados por comas
            String authorities = jwtUtils.getSpecificClaim(decodedJWT,"authorities").asString();

            //Transformamos los permisos en GrantedAuthority necesario para el context holder
            Collection<? extends GrantedAuthority> authoritiesList = AuthorityUtils.commaSeparatedStringToAuthorityList(authorities);

            //Variable del contexto de seguridad
            SecurityContext securityContext = SecurityContextHolder.getContext();

            //Nueva instancia de autenticacion, le pasamos las credenciales y permisos
            Authentication authentication = new UsernamePasswordAuthenticationToken(username,null,authoritiesList);

            //Seteamos la autenticacion al contexto de  seguridad
            securityContext.setAuthentication(authentication);

            //Seteo el contexto en el context holder
            SecurityContextHolder.setContext(securityContext);
        }

        filterChain.doFilter(request, response);

    }
}
