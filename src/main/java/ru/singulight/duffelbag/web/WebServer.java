package ru.singulight.duffelbag.web;

import java.io.*;
import java.util.*;

import org.apache.log4j.Logger;
import org.eclipse.jetty.server.*;
import org.eclipse.jetty.server.handler.*;
import org.eclipse.jetty.webapp.*;
import ru.singulight.duffelbag.web.websocket.SocketHandler;


/**
 * Example ru.singulight.WebServer class which sets up an embedded Jetty
 * appropriately whether running in an IDE or in "production"
 * mode in a shaded jar.
 */
public class WebServer
{
    private static final String LOG_PATH =
            "./var/logs/access/yyyy_mm_dd.request.log";


    public static interface WebContext
    {
        public File getWarPath();
        public String getContextPath();
    }

    private Server server;
    private int port = 8080;
    private static final Logger log = Logger.getLogger(WebServer.class);


    public void start() throws Exception
    {
        server = new Server(port);

        server.setHandler(createHandlers());
        server.setStopAtShutdown(true);

        server.start();
    }

    public void join() throws InterruptedException
    {
        server.join();
    }

    public void stop() throws Exception
    {
        server.stop();
    }


    private HandlerCollection createHandlers()
    {
        WebAppContext ctx = new WebAppContext();
        ctx.setContextPath("/");
        ctx.setResourceBase("webapp");



        List<Handler> handlers = new ArrayList<Handler>();

        handlers.add(ctx);

        HandlerList contexts = new HandlerList();
        contexts.setHandlers(handlers.toArray(new Handler[0]));

        RequestLogHandler log = new RequestLogHandler();
        log.setRequestLog(createRequestLog());

        HandlerCollection result = new HandlerCollection();
        result.setHandlers(new Handler[] {new SocketHandler(), contexts, log});

        return result;
    }

    private RequestLog createRequestLog()
    {
        NCSARequestLog log = new NCSARequestLog();

        File logPath = new File(LOG_PATH);
        logPath.getParentFile().mkdirs();

        log.setFilename(logPath.getPath());
        log.setRetainDays(90);
        log.setExtended(false);
        log.setAppend(true);
        log.setLogTimeZone("GMT");
        log.setLogLatency(true);
        return log;
    }

}