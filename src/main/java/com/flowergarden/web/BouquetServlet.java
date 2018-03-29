package com.flowergarden.web;

import com.flowergarden.model.bouquet.Bouquet;
import com.flowergarden.service.BouquetService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class BouquetServlet extends HttpServlet {

    private final BouquetService bouquetService;

    public BouquetServlet(BouquetService bouquetService) {
        this.bouquetService = bouquetService;
    }

    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try (PrintWriter out = resp.getWriter()) {
            String stringId = req.getParameter("id");
            if (stringId != null) {
                resp.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);

                int id;
                try {
                    id = Integer.parseInt(stringId);
                } catch (NumberFormatException e) {
                    resp.setStatus(HttpStatus.UNPROCESSABLE_ENTITY.value());
                    return;
                }

                if (id < 1) {
                    resp.setStatus(HttpStatus.UNPROCESSABLE_ENTITY.value());
                    return;
                }

                if (!bouquetService.exists(id)) {
                    resp.setStatus(HttpStatus.NOT_FOUND.value());
                    return;
                }

                out.print(bouquetService.getBouquetJson(id));
            } else {
                resp.setContentType(MediaType.TEXT_HTML_VALUE);

                Iterable<Bouquet> bouquets = bouquetService.findAll();
                for (Bouquet bouquet : bouquets) {
                    out.print("<a href=\"/bouquet?id=" + bouquet.getId() + "\">Bouquet " + bouquet.getId() + "</a><br />");
                }
            }
            out.print(System.lineSeparator());
        }
    }
}
