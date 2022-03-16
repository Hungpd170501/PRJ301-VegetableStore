<%-- 
    Document   : login
    Created on : Feb 10, 2022, 10:21:54 AM
    Author     : ACER
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Login Page</title>
        <link rel="stylesheet" type="text/css" href="//fonts.googleapis.com/css?family=Open+Sans" />
        <link rel="stylesheet" href="css/styleLogin.css"/>
    </head>
    <body>
        <h1>Please sign in</h1>
        <form action="MainController" method="POST">
            User ID: <input type="text" name="userID" required="" placeholder=""/></br>
            Password: <input type="password" name="password" required="" placeholder=""/></br>
            <input type="submit" name="action" value="Login"/>
            <input type="reset" value="Reset"/>
            <input type="hidden" id="recaptToken" name="recaptToken" size="1" value=""/>
        </form>
        </br>
        <a href="https://accounts.google.com/o/oauth2/auth?scope=email&redirect_uri=http://localhost:8084/VegetableStore/LoginGoogleHandler&response_type=code&client_id=786996915789-rthi7jf38p48uddnr0s9t78jumilbg5l.apps.googleusercontent.com&approval_prompt=force">
            <div class="google-btn">
                <div class="google-icon-wrapper">
                    <img class="google-icon" src="https://upload.wikimedia.org/wikipedia/commons/5/53/Google_%22G%22_Logo.svg"/>
                </div>
                <p class="btn-text"><b>Sign in with google</b></p>
            </div>     
        </a>
        </br>      
        Don't have an account?<a href="createUser.jsp">Create here</a></br> 
        <div class="g-recaptcha" data-sitekey="6LeCa7IeAAAAAFhlitWRqflibLyvju1K_P5U52dj" data-size="invisible"></div>
        <%
            String error = (String) request.getAttribute("ERROR");
            if (error == null) {
                error = "";
            }
        %>
        <%= error%>
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
