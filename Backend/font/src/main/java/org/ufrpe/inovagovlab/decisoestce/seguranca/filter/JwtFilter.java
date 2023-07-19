package org.ufrpe.inovagovlab.decisoestce.seguranca.filter;


import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;
import org.ufrpe.inovagovlab.decisoestce.seguranca.service.JwtService;


import java.io.IOException;

@Configuration
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    private JwtService jwtService;
    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");

        if(authHeader == null || !authHeader.startsWith("Bearer ")){
            filterChain.doFilter(request, response);
            return;
        }
        String jwt = authHeader.substring(7);
        String username = jwtService.extractUsername(jwt);

//        Existe usuário no token enviado e o usuário não está cadastrado
        if(username != null && SecurityContextHolder.getContext().getAuthentication() == null){
            UserDetails usuario = userDetailsService.loadUserByUsername(username);
            if(jwtService.isTokenValid(jwt, usuario)){
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(usuario, null, usuario.getAuthorities());
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }
        filterChain.doFilter(request, response);
    }
}
