package com.bocobi2.rencontre.util;

import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

public class MyHandler extends TextWebSocketHandler {

	@Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) {
        // ...
    }
	
}
