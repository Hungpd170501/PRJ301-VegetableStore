<div class="container-fluid">
    <div class="row ">
        <nav class="navbar navbar-inverse bg-primary"  role="navigation">
            <div class="container-fluid">
                <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
                    <ul class="nav navbar-nav">
                        <li ><a href="MainController?action=SearchProduct&search">Vegetable Store</a></li>
                        <li class="active"> <a href="viewProfile.jsp"> <span class="glyphicon glyphicon-home"></span> Welcome: ${sessionScope.LOGIN_USER.fullName}</a> </li>
                        <li ><a href="MainController?action=ViewCart">View Cart</a></li>
                        <li ><a href="MainController?action=Logout">Logout</a></li>
                    </ul>
                </div>
            </div>
        </nav>
    </div>
</div> 