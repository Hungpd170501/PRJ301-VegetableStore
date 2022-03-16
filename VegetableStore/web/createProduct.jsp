<%@page import="se150222.user.UserDTO"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Create Product Page</title>
    </head>
    <body>
        <%
            UserDTO loginUser = (UserDTO) session.getAttribute("LOGIN_USER");
            if (loginUser == null || !loginUser.getRoleID().equals("AD")) {
                response.sendRedirect("login.jsp");
                return;
            }
        %>
        Welcome: ${sessionScope.LOGIN_USER.fullName}</br>

        <form action="MainController" method="POST">
            <input type="submit" name="action" value="Logout">
        </form>

        <h1>Create Product</h1>    
        <form action="MainController" method="POST">
            Product ID:<input type="text" name="productID" required=""/>
            ${requestScope.PRODUCT_ERROR.getProductIDError()}</br>
            Product Name:<input type="text" name="productName" required=""/> 
            ${requestScope.PRODUCT_ERROR.getProductNameError()}</br>
            Image:<input type="text" name="image" required=""/>
            ${requestScope.PRODUCT_ERROR.getImageError()}</br>
            Price:<input type="number" required="" name="price" min="0.01" value="0.01" step="any"/> 
            ${requestScope.PRODUCT_ERROR.getPriceError()}</br>
            Quantity:<input type="number" name="quantity" min="1" value="1" required=""/>
            ${requestScope.PRODUCT_ERROR.getQuantityError()}</br>
            Category ID:
            <select name="categoryID">
                <c:forEach var="category" items="${sessionScope.LIST_CATEGORY}">
                    <option value="${category.categoryID}">${category.categoryID}</option>
                </c:forEach>
            </select></br>
            Import Date:<input type="date" name="importDate" required=""/>
            ${requestScope.PRODUCT_ERROR.getImportDateError()}</br>
            Using Date:<input type="date" name="usingDate" required=""/>
            ${requestScope.PRODUCT_ERROR.getUsingDateError()}</br>
            <input type="hidden" name="action" value="AdminCreateProduct"/>
            <input type="submit" value="Create"/>
            <input type="reset" value="Reset"/></br>
            <a href="MainController?action=AdminSearchProduct&search=">Back to Admin Page</a>
        </form>
    </body>
</html>
