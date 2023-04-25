package br.senai.sc.editoralivros.controller;

import br.senai.sc.editoralivros.model.entity.Livro;
import br.senai.sc.editoralivros.model.entity.MensagemChat;
import br.senai.sc.editoralivros.model.entity.Pessoa;
import br.senai.sc.editoralivros.service.LivroService;
import br.senai.sc.editoralivros.service.MensagemChatService;
import br.senai.sc.editoralivros.service.PessoaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ChatController {

    @Autowired
    private LivroService livroService;
    @Autowired
    private PessoaService pessoaService;
    @Autowired
    private MensagemChatService mensagemService;

    @MessageMapping("/livro/{livroId}")
    @SendTo("/livro/{livroId}/chat")
    public MensagemChat enviarMensagemLivro(@DestinationVariable Long livroId, @Payload MensagemChat mensagemChat) {
        Livro livro = livroService.findById(mensagemChat.getLivro().getIsbn()).get();
        Pessoa remetente = pessoaService.findById(mensagemChat.getRemetente().getCpf()).get();
        if (!remetente.equals(livro.getAutor()) && !remetente.equals(livro.getRevisor())) {
            throw new AccessDeniedException("Usuário não autorizado");
        }
        return mensagemService.save(mensagemChat);
    }
}
