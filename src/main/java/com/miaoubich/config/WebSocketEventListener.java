package com.miaoubich.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import com.miaoubich.chat.MessageType;

@Component
public class WebSocketEventListener {
	
	private static final Logger log = LoggerFactory.getLogger(WebSocketEventListener.class);
	private final SimpMessageSendingOperations messageTemplate;
	
	public WebSocketEventListener(SimpMessageSendingOperations messageTemplate) {
		this.messageTemplate = messageTemplate;
	}
	
	@EventListener
	public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
		log.info("handleWebSocketDisconnectListener method started!");
		
		StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
		String username = (String) headerAccessor.getSessionAttributes().get("username");
		
		if(username != null) {
			log.info("User Disconnected : " + username);
			var chatMessage = new com.miaoubich.chat.ChatMessage(
					"",
					username,
					MessageType.LEAVE
					);
			// "/topic/public" is the topic to which the message will be sent or queued and 
			// every user are listening to this topic will receive the message in the chat app
			messageTemplate.convertAndSend("/topic/public", chatMessage);
		}
	}
}
