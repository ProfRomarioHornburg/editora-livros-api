//package br.senai.sc.editoralivros.config;
//
//import org.springframework.web.socket.TextMessage;
//import org.springframework.web.socket.WebSocketSession;
//import org.springframework.web.socket.handler.TextWebSocketHandler;
//
//public class WebSocketHandler extends TextWebSocketHandler {
//
//    @Override
//    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
//        String username = (String) session.getAttributes().get("username"); // obtém o nome de usuário da sessão
//        session.getAttributes().put("username", username); // adiciona o nome de usuário como um atributo da sessão
//    }
//
//    @Override
//    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
//        String username = (String) session.getAttributes().get("username"); // obtém o nome de usuário da sessão
//        String payload = message.getPayload(); // obtém o conteúdo da mensagem
//    }
//}
