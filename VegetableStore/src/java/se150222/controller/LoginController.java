package se150222.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import se150222.user.UserDAO;
import se150222.user.UserDTO;
import se150222.utils.GoogleRacaptcha;

@WebServlet(name = "LoginController", urlPatterns = {"/LoginController"})
public class LoginController extends HttpServlet {

    private static final String ERROR = "login.jsp";
    private static final String ADMIN_PAGE = "MainController?action=AdminSearchProduct&search=";
    private static final String AD = "AD";
    private static final String USER_PAGE = "MainController?action=SearchProduct&search=";
    private static final String US = "US";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String url = ERROR;
        try {
            String userID = request.getParameter("userID");
            String password = request.getParameter("password");
            String recaptToken = request.getParameter("recaptToken");
            UserDAO dao = new UserDAO();
            UserDTO loginUser = dao.checkLogin(userID, password);
            if (!GoogleRacaptcha.isValid(recaptToken)) {
                request.setAttribute("ERROR", "There was an error processing the form, please try again!(Error verifying recaptcha)");
            } else {
                if (loginUser != null) {
                    HttpSession session = request.getSession();
                    String roleID = loginUser.getRoleID();
                    if (AD.equals(roleID)) {
                        session.setAttribute("LOGIN_USER", loginUser);
                        url = ADMIN_PAGE;
                    } else if (US.equals(roleID)) {
                        session.setAttribute("LOGIN_USER", loginUser);
                        url = USER_PAGE;
                    } else {
                        request.setAttribute("ERROR", "Your role is not support!");
                    }
                } else {
                    request.setAttribute("ERROR", "Incorrect userID or Password!");
                }

            }
        } catch (Exception e) {
            log("Error at LoginController: " + e.toString());
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
