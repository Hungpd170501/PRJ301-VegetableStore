package se150222.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import se150222.product.ProductDAO;

@WebServlet(name = "AdminDeleteProductController", urlPatterns = {"/AdminDeleteProductController"})
public class AdminDeleteProductController extends HttpServlet {

    private static final String ERROR = "AdminSearchProductController";
    private static final String SUCCESS = "AdminSearchProductController";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String url = ERROR;
        try {
            String productID = request.getParameter("productID");
            ProductDAO dao = new ProductDAO();
            boolean check = dao.deleteProduct(productID);
            if (check) {
                url = SUCCESS;
            }
        } catch (Exception e) {
            log("Error at AdminDeleteProductController: " + e.toString());
        } finally {
            request.getRequestDispatcher(url).forward(request, response);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }
}
