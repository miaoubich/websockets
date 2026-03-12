package com.miaoubich.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

/*
 * How they work together:

	1. Clients connect to the /ws endpoint
	2. Clients can send messages to /app/* destinations, which get routed to server-side message handlers
	3. Clients can subscribe to /topic/* destinations to receive broadcast messages
	4. The server can send messages to /topic/* destinations to broadcast to all subscribers

 * */

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

	@Override
	public void registerStompEndpoints(StompEndpointRegistry registry) {
		/*
		 * This method registers the WebSocket endpoint for clients to connect to It:
		 * Adds an endpoint at the path /ws that clients can connect to
		 * Enables SockJS fallback, which provides alternative transport mechanisms (like HTTP long-polling) for browsers that don't support WebSockets natively
	     * This is the URL pattern clients would use: ws://localhost:8080/ws
		 * */
		registry.addEndpoint("/ws").withSockJS();
	}
	
	@Override
	public void configureMessageBroker(MessageBrokerRegistry registry) {
		/*
		 * - Sets the prefix for messages sent from clients to the server. Messages sent to destinations starting 
		 *     with /app will be routed to controller methods (typically handled by @MessageMapping annotations)
	     * - `enableSimpleBroker("/topic")` - Enables a simple in-memory message broker for destinations starting with /topic. 
	     *     Messages sent to /topic/* paths are broadcast to all subscribed clients
		 * */
		registry.setApplicationDestinationPrefixes("/app");
		registry.enableSimpleBroker("/topic");
	}
}
