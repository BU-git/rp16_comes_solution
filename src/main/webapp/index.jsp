<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>Spring Security</title>

    <link href="<c:url value="/resources/dist/semantic.min.css" />" rel="stylesheet" type="text/css">
    <script src="<c:url value="/resources/dist/semantic.min.js" />"></script>

</head>

<body>
<button class="ui button">Test button</button>

<div>

    <div style="margin-top: 20px;">

        <sec:authorize access="!isAuthenticated()">
            <p><a href="<c:url value="/login" />" role="button">Войти</a></p>
        </sec:authorize>
        <sec:authorize access="isAuthenticated()">
            <p>Ваш логин: <sec:authentication property="principal.username" /></p>
            <p><a href="<c:url value="/logout" />" role="button">Выйти</a></p>
        </sec:authorize>
    </div>
    <div class="footer">
        <p>Truck drivers application</p>
    </div>

</div>
</body>
</html>