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

@WebServlet(name = "SearchProductController", urlPatterns = {"/SearchProductController"})
public class SearchProductController extends HttpServlet {

    private static final String ERROR = "shopping.jsp";
    private static final String SUCCESS = "shopping.jsp";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String url=ERROR;
        try {
            String search = request.getParameter("search");
            ProductDAO dao = new ProductDAO();
            List<ProductDTO> list = dao.getListValidProduct(search);
            request.setAttribute("LIST_PRODUCT", list);
            HttpSession session = request.getSession();
            CategoryDAO categoryDao = new CategoryDAO();
            List<CategoryDTO> categoryList = categoryDao.getAllCategory();
            session.setAttribute("LIST_CATEGORY", categoryList);
            url = SUCCESS;
        } catch (Exception e) {
            log("Error at SearchProductController:"+e.toString());
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
