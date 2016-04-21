<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>Track drivers login</title>

    <link href="<c:url value="/resources/dist/semantic.min.css" />" rel="stylesheet" type="text/css">
    <script src="<c:url value="/resources/dist/semantic.min.js" />"></script>
    <style>
        .jlogin {
            position: absolute;
            top: 50% !important;
            left: 50%;
            transform: translate(-50%,-50%);
            text-align: center;
            width: 40%;
        }
    </style>
</head>
        <c:url value="/j_spring_security_check" var="loginUrl"/>
        <form class="ui form jlogin" action="${loginUrl}" method="post">
            <h2 class="header">Please sign in</h2>
            <input class="field" type="email" name="j_username" placeholder="Email address" required autofocus>
            <input class="field" type="password" name="j_password" placeholder="Password" required>
            <button class="ui primary button center" type="submit">Войти</button>
            <a class="ui button" href="registration">Регистрация</a>
        </form>
</body>
</html>