<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1, minimum-scale=1, maximum-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>Track drivers login</title>

    <script src="<c:url value="/resources/vendor/js/jquery-2.2.1.js" />"></script>
    <link href="<c:url value="/resources/dist/semantic.min.css" />" rel="stylesheet" type="text/css">
    <script src="<c:url value="/resources/dist/semantic.min.js" />"></script>


    <script>
//        $(function() {
//            $('#submit').click(function () {
//                $(".ui.page.dimmer").addClass("active");
//            });
//        });

    </script>
    <style type="text/css">
        body {
            background-color: #DADADA;
        }
        body > .grid {
            height: 100%;
        }
        .image {
            margin-top: -100px;
        }
        .column {
            max-width: 450px;
        }
    </style>
</head>
<body>

<div class="ui page dimmer">
    <div class="content">
        <div class="ui loader"></div>
    </div>
</div>

<div class="ui middle aligned center aligned grid">
    <div class="column">
        <h2 class="ui teal image header">
            <i class="truck icon"></i>
            <div class="content">
                Log-in to your account
            </div>
        </h2>

        <c:url value="/j_spring_security_check" var="loginUrl"/>
        <form class="ui large form" action="${loginUrl}" method="post">


            <div class="ui stacked segment">
                <div class="field">
                    <div class="ui left icon input">
                        <i class="user icon"></i>
                        <input type="email" name="j_username" placeholder="E-mail address" required>
                    </div>
                </div>
                <div class="field">
                    <div class="ui left icon input">
                        <i class="lock icon"></i>
                        <input type="password" name="j_password" placeholder="Password" required>
                    </div>
                </div>
                <button class="ui fluid large teal button" id="submit">Submit</button>
            </div>

            <div class="ui error message">
                <c:if test="${not empty error}">
                    <div class="error">${error}</div>
                </c:if>
                <c:if test="${not empty msg}">
                    <div class="msg">${msg}</div>
                </c:if>
                <c:if test="${not empty registration}">
                    <div class="msg">${registration}</div>
                </c:if>
            </div>

            <div class="ui message">
                New to us? <a href="registration">Sign Up</a>
            </div>
        </form>
    </div>
</div>
</body>
</html>