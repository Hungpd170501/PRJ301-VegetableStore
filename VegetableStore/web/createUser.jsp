<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Create User Page</title>
    </head>
    <body>
        <h1>Create Account</h1>    
        <form action="MainController" method="POST">
            User ID:<input type="text" name="userID" required=""/>
            ${requestScope.USER_ERROR.getUserIDError()}</br>
            Full Name:<input type="text" name="fullName" required=""/> 
            ${requestScope.USER_ERROR.getFullNameError()}</br>
            Role ID:<input type="text" name="roleID" value="US" readonly=""/>
            ${requestScope.USER_ERROR.getRoleIDError()}</br>
            Password:<input type="password" name="password" required=""/> 
            ${requestScope.USER_ERROR.getPasswordError()}</br>
            Confirm Password:<input type="password" name="confirm" required=""/>
            ${requestScope.USER_ERROR.getConfirmPasswordError()}</br>
            Address:<input type="text" name="address" required=""/> 
            ${requestScope.USER_ERROR.getAddressError()}</br>
            Birthday:<input type="date" name="birthday" required=""/>
            ${requestScope.USER_ERROR.getBirthdayError()}</br>
            Phone:<input type="text" name="phone" required=""/> 
            ${requestScope.USER_ERROR.getPhoneError()}</br>
            Email:<input type="text" name="email" required=""/> 
            ${requestScope.USER_ERROR.getEmailError()}</br>
            <input type="submit" name="action" value="CreateUser"/>
            <input type="reset" value="Reset"/></br>
        </form>
        <a href="login.jsp">Login to an Existing Account</a>
    </body>
</html>
