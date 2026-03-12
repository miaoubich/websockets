package com.miaoubich.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import com.miaoubich.chat.ChatMessage;

@Controller
public class ChatController {

	@MessageMapping("/chat.sendMessage")// to which endpoint the message will be sent
	@SendTo("/topic/public")// to which topic the message will be sent
	public ChatMessage sendMessage(@Payload ChatMessage chatMessage) {
		chatMessage = new ChatMessage();
		return chatMessage;
	}
}
