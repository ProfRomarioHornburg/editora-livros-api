package br.senai.sc.editoralivros.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class MensagemChat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "livro_isbn")
    private Livro livro;
    @ManyToOne
    @JoinColumn(name = "remetente_cpf")
    private Pessoa remetente;
//    @ManyToOne
//    @JoinColumn(name = "destinatario_cpf")
//    private Pessoa destinatario;
    private String mensagem;
//    @ManyToOne
//    @JoinColumn(name = "anexo_id")
//    private Arquivo anexo;
//    private Date data;
//    @Enumerated(EnumType.STRING)
//    private StatusMensagem status;
}
