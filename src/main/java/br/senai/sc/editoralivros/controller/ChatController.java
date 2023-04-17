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
    private SimpMessagingTemplate messagingTemplate;
    @Autowired
    private LivroService livroService;
    @Autowired
    private PessoaService pessoaService;
    @Autowired
    private MensagemChatService mensagemService;

    // /chat/public-chat - /app é o prefixo configurado em WebsocketConfig
    @MessageMapping("/livro/{livroId}")
    // /chat/public - /chat é o prefixo configurado em WebsocketConfig no método registerStompEndpoints
    @SendTo("/livro/{livroId}/chat")
    public MensagemChat enviarMensagemLivro(@DestinationVariable Long livroId, @Payload MensagemChat mensagemChat) {
        // recuperar a demanda com o id fornecido
        Livro livro = livroService.findById(livroId).get();
        // verificar se o usuário atual é um dos técnicos ou o solicitante da demanda
        Pessoa remetente = pessoaService.findById(mensagemChat.getRemetente().getCpf()).get();
        if (!remetente.equals(livro.getAutor()) && !remetente.equals(livro.getRevisor())) {
            throw new AccessDeniedException("Usuário não autorizado");
        }
        // salvar a mensagem no banco de dados
        mensagemService.save(mensagemChat);
        // enviar a mensagem para o tópico do chat
        return mensagemChat;
    }

    @MessageMapping("/pessoa/{pessoaId}")
    // /chat/public - /chat é o prefixo configurado em WebsocketConfig no método registerStompEndpoints
//    @SendTo("/pessoa/chat/{pessoaId}")
    public void enviarMensagemPessoa(@DestinationVariable Long pessoaId, @Payload MensagemChat mensagemChat) {
        // Recuperar a pessoa com o ID fornecido
        Pessoa destinatario = pessoaService.findById(pessoaId).orElseThrow(() -> new IllegalArgumentException("Pessoa não encontrada"));

        // Verificar se o remetente é autorizado a enviar mensagens para o destinatário
        Pessoa remetente = pessoaService.findById(mensagemChat.getRemetente().getCpf()).orElseThrow(() -> new AccessDeniedException("Usuário não autorizado"));
        if (!remetente.equals(destinatario)) {
            throw new AccessDeniedException("Usuário não autorizado");
        }

        // Salvar a mensagem no banco de dados
        mensagemService.save(mensagemChat);

        // Enviar a mensagem para o usuário específico
        messagingTemplate.convertAndSendToUser(destinatario.getCpf().toString(), "/chat", mensagemChat);
    }

//    @MessageMapping("/pessoa")
//    public void processPrivateMessage(@Payload MensagemChat mensagemChat) {
//        // o convertAndSendToUser envia a mensagem para o usuário especificado
//        // para este método funcionar, é necessário configurar o prefixo /user em WebsocketConfig
//        // os parâmetros são: destinatário, rota, mensagem
//        messagingTemplate.convertAndSendToUser(mensagemChat.getDestinatario().getCpf().toString(), "", mensagemChat);
//    }
}
