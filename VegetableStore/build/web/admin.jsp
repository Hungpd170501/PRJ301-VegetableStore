<%@page import="se150222.user.UserDTO"%>
<%@page import="se150222.product.ProductDTO"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Admin Page</title>
    </head>
    <body>
        <%
            UserDTO loginUser = (UserDTO) session.getAttribute("LOGIN_USER");
            if (loginUser == null|| !loginUser.getRoleID().equals("AD")) {
                response.sendRedirect("login.jsp");
                return;
            }
        %>
        Welcome: <%= loginUser.getFullName()%></br>
        <a href="createProduct.jsp">Create new product</a></br> 
        <form action="MainController" method="POST">
            <input type="submit" name="action" value="Logout">
        </form>
        <form action="MainController">
            Search: <input type="text" name="search" value="${param.search}" placeholder="">
            <input type="hidden" name="action" value="AdminSearchProduct"/>
            <input type="submit" value="Search"/></br>
        </form>
        <%
            List<ProductDTO> listUser = (List<ProductDTO>) request.getAttribute("LIST_PRODUCT");
            if (listUser != null) {
                if (listUser.size() > 0) {
        %>
        <table border="1">
            <thead>
                <tr>
                    <th>NO</th>
                    <th>Product ID</th>
                    <th>Name</th>
                    <th>Image ID</th>
                    <th>Price</th>
                    <th>Quantity</th>
                    <th>Category ID</th>
                    <th>Import Date</th>
                    <th>Using Date</th>
                    <th>Delete</th>
                    <th>Update</th>
                </tr>
            </thead>
            <tbody>
                <%
                    int count = 1;
                    for (ProductDTO product : listUser) {
                %>
            <form action="MainController">
                <tr>
                    <td><%=count++%></td>
                    <td>                      
                        <input type="text" name="productID" value="<%=product.getProductID()%>" readonly="">
                    </td>
                    <td>
                        <input type="text" name="productName" value="<%=product.getProductName()%>" required="">
                    </td>
                    <td>
                        <input type="text" name="image" value="<%=product.getImage()%>" required="">
                    </td>
                    <td>
                        <input type="number" name="price" min="0.01" step="any" value="<%=product.getPrice()%>" required="">
                    </td>
                    <td>
                        <input type="number" name="quantity" min="1" value="<%=product.getQuantity()%>" required="">
                    </td>
                    <td>
                        <input type="text" name="categoryID" value="<%=product.getCategoryID()%>" readonly="">
                    </td>
                    <td>
                        <input type="date" name="importDate" value="<%=product.getImportDate()%>" required="">
                    </td>
                    <td>
                        <input type="date" name="usingDate" value="<%=product.getUsingDate()%>" min="<%=product.getImportDate()%>" required="">
                    </td>

                    <td>
                        <a href="MainController?action=AdminDeleteProduct&productID=<%=product.getProductID()%>&search=${param.search}">Delete</a>
                    </td>
                    <td>
                        <input type="hidden" name="action" value="AdminUpdateProduct"/>
                        <input type="submit" value="Update">
                        <input type="hidden" name="search" value="${param.search}">
                    </td>
                </tr>
            </form>
        </tbody>
        <%
            }
        %>
    </table>
    <p style="color:red;">
        <%
            String error = (String) request.getAttribute("ERROR");
            if (error == null) {
                error = "";
            }
        %>
        <%=error%>            
    </p>
    <%
            }
        }
    %>
</body>
</html>
