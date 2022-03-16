package se150222.controller;

import java.io.IOException;
import java.sql.Date;
import java.util.Calendar;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import se150222.product.ProductDAO;
import se150222.product.ProductDTO;
import se150222.product.ProductError;

@WebServlet(name = "AdminCreateProductController", urlPatterns = {"/AdminCreateProductController"})
public class AdminCreateProductController extends HttpServlet {

    private static final String ERROR = "createProduct.jsp";
    private static final String SUCCESS = "MainController?action=AdminSearchProduct&search=";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String url = ERROR;
        ProductError productError = new ProductError();
        try {
            String productID = request.getParameter("productID");
            String productName = request.getParameter("productName");
            String image = request.getParameter("image");
            float price = Float.parseFloat(request.getParameter("price"));
            int quantity = Integer.parseInt(request.getParameter("quantity"));
            String categoryID = request.getParameter("categoryID");
            Date importDate = Date.valueOf(request.getParameter("importDate"));
            Date usingDate = Date.valueOf(request.getParameter("usingDate"));
            boolean checkValidation = true;

            if (productID.length() > 10 || productID.length() < 2) {
                productError.setProductIDError("Product ID length must be in [2, 10]!");
                checkValidation = false;
            }
            if (productName.length() > 50 || productName.length() < 2) {
                productError.setProductNameError("Product Name length must be in [2, 50]!");
                checkValidation = false;

            }
            if (image.length() > 100 || image.length() < 2) {
                productError.setImageError("Image URL length must be in [2, 100]!");
                checkValidation = false;
            }
            if (usingDate.before(importDate) || usingDate.before(new java.sql.Date(Calendar.getInstance().getTime().getTime()))) {
                productError.setUsingDateError("Using Date must be after Import Date and the current Date");
                checkValidation = false;
            }

            if (checkValidation) {
                ProductDAO productDAO = new ProductDAO();
                ProductDTO product = new ProductDTO(productID, productName, image, price, quantity, categoryID, importDate, usingDate);
                boolean checkDuplicated = productDAO.checkDuplicateProductID(productID);
                if (checkDuplicated) {
                    productError.setProductIDError("Duplicated Product ID: " + productID + " !");
                    request.setAttribute("PRODUCT_ERROR", productError);
                } else {
                    url = SUCCESS + productName;
                    productDAO.create(product);
                }
            } else {
                request.setAttribute("PRODUCT_ERROR", productError);
            }
        } catch (Exception e) {
            log("Error at AdminCreateProductController:" + e.toString());
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
