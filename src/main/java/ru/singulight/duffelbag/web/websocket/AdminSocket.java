package ru.singulight.duffelbag.web.websocket;

import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketClose;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;

import java.io.IOException;

/**
 * Created by Grigorii Nizovoy info@singulight.ru on 10.11.16.
 */
@WebSocket
public class AdminSocket {
    private Session session;

    @OnWebSocketConnect
    public void onConnect(Session session) {
        this.session = session;
        try {
            session.getRemote().sendString("Server say: Connect!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @OnWebSocketMessage
    public void onMessage(String message) {

    }

    @OnWebSocketClose
    public void onClose (int statusCode, String reason) {

    }
}
