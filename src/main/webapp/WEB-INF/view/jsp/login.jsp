<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%--
  Created by IntelliJ IDEA.
  User: user
  Date: 17.03.2016
  Time: 3:14
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>

    <title>Title</title>
</head>
<body>

<form:form method="POST" action="login" modelAttribute="Login">
    <table>
        <tr>
            <td>Login</td>
            <td><form:input path="login" /></td>
        </tr>
        <tr>
            <td>Age</td>
            <td><form:input path="password" /></td>
        </tr>
        <tr>
            <td colspan="2">
                <input type="submit" value="Submit"/>
            </td>
        </tr>
    </table>
</form:form>

</body>
</html>
