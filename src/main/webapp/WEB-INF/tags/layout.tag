<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>

<!DOCTYPE html>
<%@tag description="Simple Template" pageEncoding="UTF-8" %>

<%@attribute name="title" %>
<%@attribute name="head_area" fragment="true" %>
<%@attribute name="body_area" fragment="true" %>


<html>
<head>

    <meta charset="utf-8"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0">
    <link rel="shortcut icon" href="/resources/images/truck-icon.png" type="image/png">

    <title>${title}</title>

    <script src="<c:url value="/resources/vendor/js/jquery-2.2.1.js" />"></script>
    <link href="<c:url value="/resources/dist/semantic.min.css" />" rel="stylesheet" type="text/css">
    <script src="<c:url value="/resources/dist/semantic.min.js" />"></script>

    <jsp:invoke fragment="head_area"/>

    <style type="text/css">
        body {
            background-color: #FFFFFF;
        }

        .ui.menu .item img.logo {
            margin-right: 1.5em;
        }

        .main.container {
            margin-top: 7em;
        }

        .wireframe {
            margin-top: 2em;
        }

        .ui.footer.segment {
            margin: 5em 0em 0em;
            padding: 5em 0em;
        }
    </style>

</head>
<body>

<div class="ui fixed inverted menu">
    <div class="ui container">
        <a href="#" class="header item">
            <i class="truck icon"></i>
            TDA
        </a>
        <a href="#" class="item">Home</a>
        <div class="ui simple dropdown item">
            Dropdown <i class="dropdown icon"></i>
            <div class="menu">
                <a class="item" href="#">Link Item</a>
                <a class="item" href="#">Link Item</a>
                <div class="divider"></div>
                <div class="header">Header Item</div>
                <div class="item">
                    <i class="dropdown icon"></i>
                    Sub Menu
                    <div class="menu">
                        <a class="item" href="#">Link Item</a>
                        <a class="item" href="#">Link Item</a>
                    </div>
                </div>
                <a class="item" href="#">Link Item</a>
            </div>
        </div>
        <div class="right menu">
            <sec:authorize access="!isAuthenticated()">
                <div class="item">
                    <a href="login" class="ui inverted grey button">Log in</a>
                </div>
                <div class="item">
                    <a href="registration" class="ui inverted grey button">Sign Up</a>
                </div>
            </sec:authorize>
            <sec:authorize access="isAuthenticated()">
                <div class="ui simple dropdown item">
                    <sec:authentication property="principal.username"/> <i class="dropdown icon"></i>
                    <div class="menu">
                        <a class="item" href="#"><i class="user icon"></i>Edit my profile</a>
                        <div class="divider"></div>
                        <a href="<c:url value="/logout" />" class="item"><i class="sign out icon"></i>Logout</a>
                    </div>
                </div>
            </sec:authorize>
        </div>
    </div>
</div>

<div class="ui main text container">
    <jsp:invoke fragment="body_area"/>
</div>

<div class="ui inverted vertical footer segment">
    <div class="ui center aligned container">
        <i class="truck icon centered"></i>
        <div class="ui horizontal inverted small divided link list">
            <a class="item" href="#">Site Map</a>
            <a class="item" href="#">Contact Us</a>
            <a class="item" href="#">Terms and Conditions</a>
            <a class="item" href="#">Privacy Policy</a>
        </div>
    </div>
</div>

</body>

</html>



