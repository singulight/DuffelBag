package ru.singulight.duffelbag.web;

import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

import ru.singulight.duffelbag.mqttnodes.*;

/**
 * Created by Grigorii Nizovoi info@singulight.ru on 03.03.16.
 */
public class MainHandler extends AbstractHandler {
    @Override
    public void handle(String s, Request request, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws IOException, ServletException {

        httpServletResponse.setContentType("text/html; charset=utf-8");
        httpServletResponse.setStatus(HttpServletResponse.SC_OK);

        PrintWriter httpOut = httpServletResponse.getWriter();

        httpOut.println("Sensors count: " + AllNodes.allSensors.size() + "<br/>");
        httpOut.println("<table> <tr><th>id</th> <th>Mqtt topic</th> <th>Value</th> <tr>");

        for (SensorNode eachSensor : AllNodes.allSensors) {
            httpOut.println("<tr> <td>" + eachSensor.getId() + "</td> <td>" + eachSensor.getMqttTopic() +
                    "</td> <td>" + eachSensor.getValue() + "</td> </tr>");
        }
        httpOut.println("</table>");
        request.setHandled(true);
    }
}