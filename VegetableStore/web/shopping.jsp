<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="se150222.user.UserDTO"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>User Page</title>
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link rel="stylesheet" href="css/bootstrap.css">
        <link rel="stylesheet" href="css/styleshopping.css">
    </head>
    <body>
        <%
            UserDTO loginUser = (UserDTO) session.getAttribute("LOGIN_USER");
            if (loginUser == null || !loginUser.getRoleID().equals("US")) {
                response.sendRedirect("login.jsp");
                return;
            }
        %>
        <div class="container-fluid">
            <div class="row">
                <nav class="navbar navbar-inverse bg-primary"  role="navigation">
                    <div class="container-fluid">
                        <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
                            <ul class="nav navbar-nav">
                                <li ><a href="MainController?action=SearchProduct&search">Vegetable Store</a></li>
                                <li class="active"> <a href="viewProfile.jsp"> <span class="glyphicon glyphicon-home"></span> Welcome: <%= loginUser.getFullName()%></a> </li>
                                <li ><a href="MainController?action=ViewCart">View Cart</a></li>
                                <li ><a href="MainController?action=Logout">Logout</a></li>
                            </ul>
                            <form action="MainController" class="navbar-form navbar-right" role="search">
                                <div class="form-group">
                                    <input type="text" name="search" value="${param.search}" placeholder="" class="form-control">
                                </div>
                                <input type="hidden" name="action" value="SearchProduct"/>
                                <button type="submit" class="btn btn-default">Search</button>

                            </form>

                        </div>
                    </div>
                </nav>
            </div>
            <div class="row row-no-padding">
                <div class="col-md-12 nopadding">
                    <img class="img-responsive center-block w-100" src="images/banner3.jpg" alt="banner img"/>
                </div>
            </div>
        </div> 
        </br>
        <div class="list-group list-group-horizontal justify-content-center">
            <c:forEach var="dto" items="${sessionScope.LIST_CATEGORY}">
                <a href="#" class="list-group-item">${dto.categoryName}</a>
                <!--<form action="MainController">-->
                <!--<button type="submit"  name="action" value="SearchProductByCategory" class="btn btn-link"></button>-->
                <!--</form>-->
            </c:forEach>                
        </div>
        <div class="container" style="text-align: center;">
            <c:forEach var="dto" items="${requestScope.LIST_PRODUCT}">
                <form action="MainController">
                    <div class="col-md-3">
                        <div style="position: relative;">
                            <img class="img-responsive" src="${dto.image}" alt="product img" onerror="this.src='images/no-image.jpg';" style="width:400px; height:400px;"/>
                            <div class="service_item">
                                <h3>${dto.productName}</h3>
                                <input type="hidden" name="productID" value="${dto.productID}"/>
                                <p style="color: white">${dto.price}$</p>
                                <input type="number" name="quantity" value="1" min="1" max="${dto.quantity}" style="color: black"/>
                                <input type="hidden" name="search" value="${requestScope.search}">
                                <input type="hidden" name="action" value="AddToCart"/>            
                                <input type="submit" value="Add" style="color: black"/>
                            </div>
                        </div>
                    </div>
                </form>
            </c:forEach>

        </div>

        <div class="container-fluid">
            <div class="row">
                <div class="text-center footer">
                    <p>Email : VegetableStore@gmail.com</p>
                    <p>Address: D1 Street, High Tech Park, 9 District, HCMC</p>
                    <h5>&copy; Copyright 2021. VegetableStore.VN</h5>
                </div>
            </div>
        </div>   
    </body>
</html>
