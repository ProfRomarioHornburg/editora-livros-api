package br.senai.sc.editoralivros.security;

import br.senai.sc.editoralivros.security.jwt.JwtUtils;
import br.senai.sc.editoralivros.security.service.JpaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.header.writers.StaticHeadersWriter;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RestController
@RequestMapping("/login")
//@CrossOrigin
public class AutenticacaoController {

    @Autowired
    private AuthenticationManager authenticationManager;

    private final JwtUtils jwtUtils = new JwtUtils();

    @PostMapping("/auth")
    public ResponseEntity<Object> autenticacao(
            @RequestBody UsuarioDTO usuarioDTO
            ,HttpServletResponse response
    ) {
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(
                        usuarioDTO.getEmail(),usuarioDTO.getSenha());
        Authentication authentication =
                authenticationManager.authenticate(authenticationToken);
        if (authentication.isAuthenticated()) {
            Cookie cookie = jwtUtils.gerarTokenCookies(authentication);
            response.addCookie(cookie);
            return ResponseEntity.status(HttpStatus.OK).body(authentication.getPrincipal());
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

}
