package br.senai.sc.editoralivros.model.entity;

import br.senai.sc.editoralivros.model.enums.Genero;
import br.senai.sc.editoralivros.model.enums.StatusChat;
import lombok.*;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.oauth2.core.user.OAuth2User;

import javax.persistence.*;

@Entity
@Table(name = "tb_pessoa")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Pessoa {

    @Id
    @Column(length = 11, nullable = false, unique = true)
    private Long cpf;

    @Column(length = 50, nullable = false)
    private String nome;

    @Column(length = 100, nullable = false)
    private String sobrenome;

    @Column(length = 150, nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String senha;

    @Enumerated(value = EnumType.STRING)
    @Column(length = 15, nullable = false)
    private Genero genero;

    @Enumerated(value = EnumType.STRING)
    private StatusChat statusChat;

}
