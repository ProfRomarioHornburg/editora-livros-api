package br.senai.sc.editoralivros.security.jwt;

import br.senai.sc.editoralivros.security.service.JpaService;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;

@AllArgsConstructor
public class AutenticacaoFiltro extends OncePerRequestFilter {

    private final JwtUtils jwtUtils = new JwtUtils();

    private JpaService jpaService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (       "/login/auth".                       equals(request.getRequestURI())
                || "/logout".                           equals(request.getRequestURI())
                || "http://localhost:3000/login".       equals(request.getRequestURI())
                || "https://localhost:3000/login".      equals(request.getRequestURI())
                || "http://editorasenaiweb:3000/login". equals(request.getRequestURI())
                || "https://editorasenaiweb:3000/login".equals(request.getRequestURI())
                || "http://nginx:80/login".             equals(request.getRequestURI())
                || "https://nginx:443/login".           equals(request.getRequestURI())) {
            filterChain.doFilter(request, response);
            return;
        }
        String token = jwtUtils.getJwtCookies(request);
        if (token != null) {
            if (jwtUtils.validarToken(token)) {
                Long cpf = jwtUtils.getCpf(token);
                UserDetails usuario = jpaService.getUser(cpf);
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                        new UsernamePasswordAuthenticationToken(usuario.getUsername(),
                                usuario.getPassword(), usuario.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(
                        usernamePasswordAuthenticationToken);
                filterChain.doFilter(request, response);
                return;
            }
        }
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
    }
}
