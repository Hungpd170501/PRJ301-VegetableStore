package se150222.controller;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import se150222.category.CategoryDAO;
import se150222.category.CategoryDTO;
import se150222.product.ProductDAO;
import se150222.product.ProductDTO;

@WebServlet(name = "AdminSearchProductController", urlPatterns = {"/AdminSearchProductController"})
public class AdminSearchProductController extends HttpServlet {

    private static final String ERROR = "admin.jsp";
    private static final String SUCCESS = "admin.jsp";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String url = ERROR;
        try {
            HttpSession session = request.getSession();
            CategoryDAO categoryDao = new CategoryDAO();
            List<CategoryDTO> categoryList = categoryDao.getAllCategory();
            session.setAttribute("LIST_CATEGORY", categoryList);

            String search = request.getParameter("search");
            ProductDAO dao = new ProductDAO();
            List<ProductDTO> list = dao.getListProduct(search);
            request.setAttribute("LIST_PRODUCT", list);
            url = SUCCESS;
        } catch (Exception e) {
            log("Error at AdminSearchProductController:" + e.toString());
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
