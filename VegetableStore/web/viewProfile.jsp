<%-- 
    Document   : viewProfile
    Created on : Mar 9, 2022, 1:29:30 PM
    Author     : ACER
--%>

<%@page import="se150222.user.UserDTO"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>View Profile Page</title>
        <link rel="stylesheet" href="css/bootstrap.css">
    </head>
    <body>
        <jsp:include page="nav.jsp"></jsp:include>
        <%
            UserDTO loginUser = (UserDTO) session.getAttribute("LOGIN_USER");
            if (loginUser == null || !loginUser.getRoleID().equals("US")) {
                response.sendRedirect("login.jsp");
                return;
            }
        %>
        <h3>Your information: </h3>   
        User ID: ${sessionScope.LOGIN_USER.getUserID()}</br>
        Full Name: ${sessionScope.LOGIN_USER.getFullName()}</br>
        Role ID: ${sessionScope.LOGIN_USER.getRoleID()}</br>
        Address: ${sessionScope.LOGIN_USER.getAddress()}</br>
        Birthday: ${sessionScope.LOGIN_USER.getBirthday()}</br>
        Phone: ${sessionScope.LOGIN_USER.getPhone()}</br>
        Email: ${sessionScope.LOGIN_USER.getEmail()}</br>
        Password: ***</br>
        <a href="MainController?action=SearchProduct&search=">Continue Shopping</a>
    </body>
</html>
