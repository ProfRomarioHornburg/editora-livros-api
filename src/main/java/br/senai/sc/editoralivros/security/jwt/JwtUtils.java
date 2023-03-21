package br.senai.sc.editoralivros.security.jwt;

import br.senai.sc.editoralivros.security.users.UserJpa;
import io.jsonwebtoken.*;
import org.springframework.security.core.Authentication;
import org.springframework.web.util.WebUtils;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;

public class JwtUtils {

    private final String senhaForte = "c127a7b6adb013a5ff879ae71afa62afa4b4ceb72afaa54711dbcde67b6dc325";

    public String gerarToken(Authentication authentication) {
        UserJpa usuario = (UserJpa) authentication.getPrincipal();
        return Jwts.builder()
                .setIssuer("Editora de Livros")
                .setSubject(usuario.getPessoa().getCpf().toString())
                .setIssuedAt(new Date())
                .setExpiration(new Date(new Date().getTime() + 1800000))
                .signWith(SignatureAlgorithm.HS256, senhaForte)
                .compact();
    }

    public Cookie gerarTokenCookies(Authentication authentication) {
        Cookie cookie = new Cookie("jwt", gerarToken(authentication));
        cookie.setPath("/");
        cookie.setSecure(true);
        cookie.setMaxAge(3600);
        return cookie;
    }

    public Boolean validarToken(String token) {
        System.out.println(Jwts.parser().setSigningKey(senhaForte).parseClaimsJws(token));
        try {
            System.out.println("Token válido");
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public String getJwtCookies(HttpServletRequest request) {
        Cookie cookie = WebUtils.getCookie(request, "jwt");
        return cookie.getValue();
    }

    public Long getCpf(String token) {
        return Long.parseLong(Jwts.parser().
                setSigningKey(senhaForte).
                parseClaimsJws(token).
                getBody().
                getSubject());
    }


}
