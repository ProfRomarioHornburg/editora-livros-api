package br.senai.sc.editoralivros.config;

import br.senai.sc.editoralivros.model.entity.Livro;
import br.senai.sc.editoralivros.model.entity.MensagemChat;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.converter.DefaultContentTypeResolver;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebsocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.setApplicationDestinationPrefixes("/editora-livros-api");
//        registry.enableSimpleBroker("/livro", "/pessoa");
//        registry.setUserDestinationPrefix("/pessoa");
    }
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/chat").
                setAllowedOrigins("http://localhost:3000")
                .withSockJS()
                .setSessionCookieNeeded(true);
    }

//    @MessageMapping("/livro/{isbn}")
//    @SendTo("/livro/{isbn}")
//    public MensagemChat handleLivro(MensagemChat mensagemChat) {
//        System.out.println("mensagem recebida: " + mensagemChat.getMensagem());
//        // Lógica de manipulação do livro recebido
//        return mensagemChat;
//    }

}
