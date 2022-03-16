<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="se150222.user.UserDTO"%>
<%@page import="se150222.product.ProductDTO"%>
<%@page import="se150222.cart.Cart"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>View Cart Page</title>
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

        <%
            Cart cart = (Cart) session.getAttribute("CART");
            if (cart == null) {
        %>
        Your cart is Empty!
        <%
        } else {
            if (cart.getCart().size() != 0) {
        %>
        <h1>Your cart:</h1>
        <table border="1">
            <thead>
                <tr>
                    <th>No</th>
                    <th>ID</th>
                    <th>Name</th>
                    <th>Price</th>
                    <th>Quantity</th>
                    <th>Total</th>
                    <th>Remove</th>
                    <th>Edit</th>

                </tr>
            </thead>
            <tbody>
                <%
                    int count = 1;
                    double total = 0;
                    for (ProductDTO product : cart.getCart().values()) {
                        total += product.getPrice() * product.getQuantity();
                %>
            <form action="MainController">
                <tr>
                    <td><%=count++%></td>
                    <td>
                        <%=product.getProductID()%>
                        <input type="hidden" name="id" value="<%=product.getProductID()%>"
                    </td>
                    <td><%=product.getProductName()%></td>
                    <td><%=product.getPrice()%></td>
                    <td>
                        <input type="number" name="quantity" value="<%=product.getQuantity()%>" min="1"/>
                    </td>
                    <td> <%=Math.round((product.getPrice() * product.getQuantity()) * 100.0) / 100.0%></td>
                    <td>
                        <a href="MainController?action=RemoveCart&id=<%=product.getProductID()%>">Remove</a>
                    </td>
                    <td>
                        <input type="hidden" name="action" value="UpdateCart"/>
                        <input type="submit" value="Edit">
                    </td>
                </tr>
            </form>    
            <%}
            %>
        </tbody>
    </table></br>
    <form action="MainController" method="POST">
        <input type="hidden" name="action" value="CheckOut"/>
        <input type="hidden" id="recaptToken" name="recaptToken" size="1" value=""/>
        <input type="hidden" name="total" value="<%=Math.round(total * 100.0) / 100.0%>"/>

        <input type="submit" value="Check Out"/>
    </form>
    <h2>Total: <%=Math.round(total * 100.0) / 100.0%> $</h2>
    <%
            }
        }
    %>
    <h2>${requestScope.ORDER_MESSAGE}</h2>
    <h3>${requestScope.ERROR}</h3>
    </br>
    <a href="MainController?action=SearchProduct&search=${requestScope.search}">Add more</a>
    <div class="g-recaptcha" data-sitekey="6LeCa7IeAAAAAFhlitWRqflibLyvju1K_P5U52dj" data-size="invisible"></div>
    <script src="https://www.google.com/recaptcha/api.js?render=6LeCa7IeAAAAAFhlitWRqflibLyvju1K_P5U52dj"></script>
    <script>
        grecaptcha.ready(function () {
            grecaptcha.execute('6LeCa7IeAAAAAFhlitWRqflibLyvju1K_P5U52dj', {action: ''}).then(function (token) {
                document.getElementById('recaptToken').value = token;
            });
        });
    </script>
</body>
</html>
