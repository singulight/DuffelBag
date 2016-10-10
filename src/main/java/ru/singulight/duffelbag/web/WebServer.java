package ru.singulight.duffelbag.web;

import java.io.*;
import java.util.*;

import org.apache.log4j.Logger;
import org.eclipse.jetty.server.*;
import org.eclipse.jetty.server.handler.*;
import org.eclipse.jetty.webapp.*;


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
        WebAppContext _ctx = new WebAppContext();
        _ctx.setContextPath("/");
        _ctx.setResourceBase("webapp");


        List<Handler> _handlers = new ArrayList<Handler>();

        _handlers.add(_ctx);

        HandlerList _contexts = new HandlerList();
        _contexts.setHandlers(_handlers.toArray(new Handler[0]));

        RequestLogHandler _log = new RequestLogHandler();
        _log.setRequestLog(createRequestLog());

        HandlerCollection _result = new HandlerCollection();
        _result.setHandlers(new Handler[] {_contexts, _log});

        return _result;
    }

    private RequestLog createRequestLog()
    {
        NCSARequestLog _log = new NCSARequestLog();

        File _logPath = new File(LOG_PATH);
        _logPath.getParentFile().mkdirs();

        _log.setFilename(_logPath.getPath());
        _log.setRetainDays(90);
        _log.setExtended(false);
        _log.setAppend(true);
        _log.setLogTimeZone("GMT");
        _log.setLogLatency(true);
        return _log;
    }

}