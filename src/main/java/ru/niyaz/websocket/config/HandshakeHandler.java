package ru.niyaz.websocket.config;

import org.springframework.http.server.ServerHttpRequest;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.DefaultHandshakeHandler;

import java.security.Principal;
import java.util.Map;

/**
 * Created by user on 17.04.2016.
 */
public class HandshakeHandler extends DefaultHandshakeHandler {

    public HandshakeHandler() {
        super();
    }

    @Override
    protected Principal determineUser(ServerHttpRequest request, WebSocketHandler wsHandler, Map<String, Object> attributes) {
        return super.determineUser(request, wsHandler, attributes);
    }
}
