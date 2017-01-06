package ru.singulight.duffelbag.web.websocket;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Grigorii Nizovoy info@singulight.ru on 06.01.17.
 */
public class SocketObjects {
    private static SocketObjects ourInstance = new SocketObjects();

    public static SocketObjects getInstance() {
        return ourInstance;
    }

    private SocketObjects() {
    }

    private ArrayList<AdminSocket> sockets = new ArrayList<>();

    public void join(AdminSocket socket) {
        sockets.add(socket);
    }

    public void leave(AdminSocket socket) {
        sockets.remove(socket);
    }

    public void send(String message) {
        for (AdminSocket socket: sockets) {
            try {
                socket.session.getRemote().sendString(message);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
