package ru.singulight.duffelbag.web.websocket;

import org.eclipse.jetty.websocket.server.WebSocketHandler;
import org.eclipse.jetty.websocket.servlet.WebSocketServletFactory;

/**
 * Created by Grigorii Nizovoy info@singulight.ru on 10.11.16.
 */
public class SocketHandler extends WebSocketHandler {
    @Override
    public void configure(WebSocketServletFactory webSocketServletFactory) {
        webSocketServletFactory.register(AdminSocket.class);
    }
}
