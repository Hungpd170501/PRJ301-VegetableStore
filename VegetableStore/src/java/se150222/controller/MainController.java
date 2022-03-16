package se150222.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class MainController extends HttpServlet {

    private static final String ERROR = "error.jsp";
    private static final String LOGIN = "Login";
    private static final String LOGIN_CONTROLLER = "LoginController";
    private static final String LOGOUT = "Logout";
    private static final String LOGOUT_CONTROLLER = "LogoutController";
    private static final String SEARCH_PRODUCT = "SearchProduct";
    private static final String SEARCH_PRODUCT_CONTROLLER = "SearchProductController";
    private static final String ADD_TO_CART = "AddToCart";
    private static final String ADD_TO_CART_CONTROLLER = "AddToCartController";
    private static final String UPDATE_CART = "UpdateCart";
    private static final String UPDATE_CART_CONTROLLER = "UpdateCartController";
    private static final String REMOVE_CART = "RemoveCart";
    private static final String REMOVE_CART_CONTROLLER = "RemoveCartController";
    private static final String VIEW_CART = "ViewCart";
    private static final String VIEW_CART_CONTROLLER = "viewCart.jsp";
    private static final String CHECK_OUT = "CheckOut";
    private static final String CHECK_OUT_CONTROLLER = "CheckOutController";
    private static final String ADMIN_SEARCH_PRODUCT = "AdminSearchProduct";
    private static final String ADMIN_SEARCH_PRODUCT_CONTROLLER = "AdminSearchProductController";
    private static final String ADMIN_CREATE_PRODUCT = "AdminCreateProduct";
    private static final String ADMIN_CREATE_PRODUCT_CONTROLLER = "AdminCreateProductController";
    private static final String ADMIN_DELETE_PRODUCT = "AdminDeleteProduct";
    private static final String ADMIN_DELETE_PRODUCT_CONTROLLER = "AdminDeleteProductController";
    private static final String ADMIN_UPDATE_PRODUCT = "AdminUpdateProduct";
    private static final String ADMIN_UPDATE_PRODUCT_CONTROLLER = "AdminUpdateProductController";
    private static final String CREATE_USER = "CreateUser";
    private static final String CREATE_USER_CONTROLLER = "CreateUserController";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String url = ERROR;
        try {
            String action = request.getParameter("action");
            if (LOGIN.equals(action)) {
                url = LOGIN_CONTROLLER;
            } else if (LOGOUT.equals(action)) {
                url = LOGOUT_CONTROLLER;
            } else if (SEARCH_PRODUCT.equals(action)) {
                url = SEARCH_PRODUCT_CONTROLLER;
            } else if (ADD_TO_CART.equals(action)) {
                url = ADD_TO_CART_CONTROLLER;
            }else if (UPDATE_CART.equals(action)) {
                url = UPDATE_CART_CONTROLLER;
            }else if (REMOVE_CART.equals(action)) {
                url = REMOVE_CART_CONTROLLER;
            } else if (VIEW_CART.equals(action)) {
                url = VIEW_CART_CONTROLLER;
            }else if (ADMIN_SEARCH_PRODUCT.equals(action)) {
                url = ADMIN_SEARCH_PRODUCT_CONTROLLER;
            } else if (CHECK_OUT.equals(action)) {
                url = CHECK_OUT_CONTROLLER;
            }else if (ADMIN_CREATE_PRODUCT.equals(action)) {
                url = ADMIN_CREATE_PRODUCT_CONTROLLER;
            }else if (ADMIN_DELETE_PRODUCT.equals(action)) {
                url = ADMIN_DELETE_PRODUCT_CONTROLLER;
            }else if (ADMIN_UPDATE_PRODUCT.equals(action)) {
                url = ADMIN_UPDATE_PRODUCT_CONTROLLER;
            }else if (CREATE_USER.equals(action)) {
                url = CREATE_USER_CONTROLLER;
            }
        } catch (Exception e) {
            log("Error at MainController: " + e.toString());
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
