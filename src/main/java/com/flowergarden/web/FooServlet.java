package com.flowergarden.web;

import com.flowergarden.service.BouquetService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Slf4j
public class FooServlet extends HttpServlet {

    private final BouquetService bouquetService;

    public FooServlet(BouquetService bouquetService) {
        this.bouquetService = bouquetService;
    }

    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter out = resp.getWriter();
        if (req.getParameter("id") != null) {
            int id = Integer.parseInt(req.getParameter("id"));
            log.info("{}", id);
            resp.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
            out.print(bouquetService.getBouquetJson(id));
        } else {
            resp.setContentType(MediaType.TEXT_HTML_VALUE);
            out.print("Hello Servlet!");
        }
    }
}
