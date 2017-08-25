package pe.controlador.ventas;

import com.fasterxml.jackson.core.type.TypeReference;
import pe.controlador.administracion.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import pe.controlador.JsonTransformer;
import pe.modelo.dto.ventas.Parametro;
import pe.modelo.dao.ventas.IDocumentoVentaDao;
import pe.modelo.pojo.CargaSesion;
import pe.modelo.pojo.DocumentoVenta;
import pe.modelo.pojo.TipoDocumentoVenta;

@Controller
public class DocumentoVentaController {

    @Autowired
    JsonTransformer jsonTransformer;

    @Autowired
    IDocumentoVentaDao documentoVentaDao;

    @RequestMapping(value = "/documentoventa/listar", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    public void listar(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, @RequestBody String jsonEntrada) throws IOException {
        PrintWriter out = httpServletResponse.getWriter();
        try {
            //List<Parametro> parametros = (List<Parametro>) jsonTransformer.fromJson(jsonEntrada,Parametro[].class);
            Parametro[] parametros = (Parametro[]) jsonTransformer.fromJson(jsonEntrada, Parametro[].class);
            List<DocumentoVenta> lista = documentoVentaDao.listar(parametros);
            long total = documentoVentaDao.totalDocumentoVentas(parametros);
            String jsonSalida = "{\"lista\":" + jsonTransformer.toJson(lista) + ",\"total\":" + total + "}";
            System.out.println("jsonSalida:" + jsonSalida);
            httpServletResponse.setStatus(HttpServletResponse.SC_OK);
            httpServletResponse.setContentType("application/json; charset=UTF-8");
            out.println(jsonSalida);
        } catch (Exception ex) {
            ex.printStackTrace();
            httpServletResponse.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            httpServletResponse.setContentType("application/json; charset=UTF-8");
            out.println("{\"RSP\":\"ERROR\",\"MSG\":\"" + ex.getMessage() + "\"}");
        }
    }

    @RequestMapping(value = "/documentoventa/crear", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    public void crear(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, @RequestBody String jsonEntrada) {
        try {
            DocumentoVenta documentoventa = (DocumentoVenta) jsonTransformer.fromJson(jsonEntrada, DocumentoVenta.class);
            documentoVentaDao.crear(documentoventa);
            if (documentoventa.getId() > 0) {
                String jsonSalida = jsonTransformer.toJson(documentoventa);
                httpServletResponse.setStatus(HttpServletResponse.SC_OK);
                httpServletResponse.setContentType("application/json; charset=UTF-8");
                httpServletResponse.getWriter().println(jsonSalida);
            } else {
                String jsonSalida = jsonTransformer.toJson(documentoventa);
                httpServletResponse.setStatus(HttpServletResponse.SC_EXPECTATION_FAILED);
                httpServletResponse.setContentType("application/json; charset=UTF-8");
                httpServletResponse.getWriter().println(jsonSalida);
            }

        } catch (Exception ex) {
            httpServletResponse.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            httpServletResponse.setContentType("text/plain; charset=UTF-8");
            try {
                ex.printStackTrace(httpServletResponse.getWriter());
            } catch (IOException ex1) {
                Logger.getLogger(DocumentoVentaController.class.getName()).log(Level.SEVERE, null, ex1);
            }
        }
    }

    @RequestMapping(value = "/documentoventa/editar", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    public void editar(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, @RequestBody String jsonEntrada) throws IOException {
        PrintWriter out = httpServletResponse.getWriter();
        try {
            DocumentoVenta documentoventa = (DocumentoVenta) jsonTransformer.fromJson(jsonEntrada, DocumentoVenta.class);
            documentoVentaDao.editar(documentoventa);
            String jsonSalida = jsonTransformer.toJson(documentoventa);
            httpServletResponse.setStatus(HttpServletResponse.SC_OK);
            httpServletResponse.setContentType("application/json; charset=UTF-8");
            out.println(jsonSalida);
        } catch (Exception ex) {
            ex.printStackTrace();
            httpServletResponse.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            httpServletResponse.setContentType("application/json; charset=UTF-8");
            out.println("{\"RSP\":\"ERROR\",\"MSG\":\"" + ex.getMessage() + "\"}");
        }
    }

    @RequestMapping(value = "/documentoventa/eliminar/{id}", method = RequestMethod.POST, produces = "application/json")
    public void eliminar(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, @PathVariable("id") long id) throws IOException {
        PrintWriter out = httpServletResponse.getWriter();
        try {
            documentoVentaDao.eliminar(id);
            httpServletResponse.setStatus(HttpServletResponse.SC_OK);
            httpServletResponse.setContentType("application/json; charset=UTF-8");
            out.println(id);
        } catch (Exception ex) {
            ex.printStackTrace();
            httpServletResponse.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            httpServletResponse.setContentType("application/json; charset=UTF-8");
            out.println("{\"RSP\":\"ERROR\",\"MSG\":\"" + ex.getMessage() + "\"}");
        }
    }
}