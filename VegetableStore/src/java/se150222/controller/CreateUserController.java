package se150222.controller;

import java.io.IOException;
import java.sql.Date;
import java.util.Calendar;
import java.util.regex.Pattern;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import se150222.user.UserDAO;
import se150222.user.UserDTO;
import se150222.user.UserError;

@WebServlet(name = "CreateUserController", urlPatterns = {"/CreateUserController"})
public class CreateUserController extends HttpServlet {

    private static final String ERROR = "createUser.jsp";
    private static final String SUCCESS = "login.jsp";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String url = ERROR;
        UserError userError = new UserError();
        try {
            String userID = request.getParameter("userID");
            String fullName = request.getParameter("fullName");
            String roleID = request.getParameter("roleID");
            String password = request.getParameter("password");
            String confirm = request.getParameter("confirm");
            String address = request.getParameter("address");
            Date birthday = Date.valueOf(request.getParameter("birthday"));
            String phone = request.getParameter("phone");
            String email = request.getParameter("email");
            boolean checkValidation = true;
            if (userID.length() > 10 || userID.length() < 2) {
                userError.setUserIDError("UserID length must be in [2, 10]!");
                checkValidation = false;
            }
            if (fullName.length() > 20 || fullName.length() < 5) {
                userError.setFullNameError("Full Name length must be in [5, 20]!");
                checkValidation = false;

            }
            if (!password.equals(confirm)) {
                userError.setConfirmPasswordError("Two password are not in the same!");
                checkValidation = false;
            }
            if (address.length() > 100 || fullName.length() < 5) {
                userError.setAddressError("Address length must be in [5, 100]!");
                checkValidation = false;

            }
            if (birthday.after(new java.sql.Date(Calendar.getInstance().getTime().getTime()))) {
                userError.setBirthdayError("Invalid birthday!");
                checkValidation = false;
            }
            Pattern regex = Pattern.compile("^[+]*[(]{0,1}[0-9]{1,4}[)]{0,1}[-\\s\\./0-9]*$");
            boolean ismatched = regex.matcher(phone).matches();
            if (!ismatched) {
                userError.setPhoneError(" Please enter a valid phone number!");
                checkValidation = false;
            }
            regex = Pattern.compile("\\b[\\w.%-]+@[-.\\w]+\\.[A-Za-z]{2,4}\\b");
            ismatched = regex.matcher(email).matches();
            if (!ismatched) {
                userError.setEmailError(" Please enter a valid Email!");
                checkValidation = false;
            }
            if (checkValidation) {
                UserDAO dao = new UserDAO();
                UserDTO user = new UserDTO(userID, fullName, password, roleID, address, birthday, phone, email);
                boolean checkDuplicatedUserID = dao.checkDuplicateUserID(userID);
                boolean checkDuplicatedEmail = dao.checkDuplicateEmail(email);
                if (!checkDuplicatedUserID && !checkDuplicatedEmail) {
                    url = SUCCESS;
                    dao.create(user);

                } else if (checkDuplicatedEmail) {
                    userError.setEmailError("Dupliated Email:" + email + " !");
                    request.setAttribute("USER_ERROR", userError);
                } else if (checkDuplicatedUserID) {
                    userError.setUserIDError("Dupliated UserID:" + userID + " !");
                    request.setAttribute("USER_ERROR", userError);
                }
            } else {
                request.setAttribute("USER_ERROR", userError);

            }
        } catch (Exception e) {
            log("Error at CreateUserController:" + e.toString());
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
