package ru.singulight.duffelbag.web;

import org.apache.log4j.Logger;
import org.eclipse.jetty.server.Server;

/**
 * Created by Grigorii Nizovoi info@singulight.ru on 04.04.16.
 */
public class InitHttpServer {

    private Server httpServer;
    private final int httpPort = 8080;

    private static final Logger log = Logger.getLogger(InitHttpServer.class);

    public void jettyStart() {
        httpServer = new Server(httpPort);
        httpServer.setHandler(new ru.singulight.duffelbag.web.MainHandler());
        try {
            httpServer.start();
            httpServer.dumpStdErr();
            httpServer.join();
        } catch (Exception e) {
            log.error("Fail to create http server",e);
        }

    }
}
