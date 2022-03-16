package se150222.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import se150222.cart.Cart;
import se150222.order.OrderDAO;
import se150222.order.OrderDTO;
import se150222.orderDetail.OrderDetailDAO;
import se150222.orderDetail.OrderDetailDTO;
import se150222.product.ProductDAO;
import se150222.product.ProductDTO;
import se150222.user.UserDTO;
import se150222.utils.GoogleRacaptcha;

@WebServlet(name = "CheckOutController", urlPatterns = {"/CheckOutController"})
public class CheckOutController extends HttpServlet {

    private static final String ERROR = "viewCart.jsp";
    private static final String SUCCESS = "viewCart.jsp";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String url = ERROR;
        try {
            String recaptToken = request.getParameter("recaptToken");
            if (!GoogleRacaptcha.isValid(recaptToken)) {
                request.setAttribute("ERROR", "There was an error processing the form, please try again!(Error verifying recaptcha)");
            } else {
                HttpSession session = request.getSession();
                boolean checkQuantity = true;
                ProductDAO productDAO = new ProductDAO();
                Cart cart = (Cart) session.getAttribute("CART");
                List<ProductDTO> productList = new ArrayList<>(cart.getCart().values());
                for (ProductDTO product : productList) {
                    boolean check = productDAO.checkQuantity(product.getProductID(), product.getQuantity());
                    if (!check) {
                        checkQuantity = false;
                        request.setAttribute("ORDER_MESSAGE", "Please check quantity again!");
                    }
                }
                if (checkQuantity) {
                    UserDTO user = (UserDTO) session.getAttribute("LOGIN_USER");
                    float total = Float.parseFloat(request.getParameter("total"));
                    OrderDAO orderDAO = new OrderDAO();
                    java.sql.Date sqlDate = new java.sql.Date(Calendar.getInstance().getTime().getTime());
                    int orderID = orderDAO.create(new OrderDTO(sqlDate, total, user.getUserID()));
                    OrderDetailDAO orderDetailDAO = new OrderDetailDAO();
                    for (ProductDTO product : productList) {
                        orderDetailDAO.create(new OrderDetailDTO(product.getPrice(), product.getQuantity(), orderID, product.getProductID()));
                        productDAO.decreaseQuantity(product.getProductID(), product.getQuantity());
                    }
                    session.removeAttribute("CART");
                    request.setAttribute("ORDER_MESSAGE", "Order success! Thank you for purchase!");
                }
            }
        } catch (Exception e) {
            log("Error at CheckOutController: " + e.toString());
        } finally {
            request.getRequestDispatcher(url).include(request, response);
            request.removeAttribute("ORDER_MESSAGE");
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
