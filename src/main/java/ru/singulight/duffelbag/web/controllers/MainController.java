package ru.singulight.duffelbag.web.controllers;

import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

import org.luaj.vm2.ast.Str;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.singulight.duffelbag.nodes.*;

/**
 * Created by Grigorii Nizovoi info@singulight.ru on 03.03.16.
 */
@Controller
public class MainController {

    @RequestMapping("/")
    public String index(Model model) {

        return "index2";
    }
}
