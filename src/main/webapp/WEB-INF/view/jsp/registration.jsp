<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Registration</title>

    <link href="<c:url value="/resources/vendor/css/semantic.min.css" />" rel="stylesheet" type="text/css">
    <script src="<c:url value="/resources/vendor/js/semantic.min.js" />"></script>

</head>
<body>
<div class="ui middle aligned center aligned grid">
    <div class="ui centered grid">
        <div class="sixteen wide column">
            <div class="ui tree steps">
                <div class="step">
                    <i class="truck icon"></i>
                    <div class="content">
                        <div class="title">Shipping</div>
                        <div class="description">Choose your shipping options</div>
                    </div>
                </div>
                <div class="active step">
                    <i class="payment icon"></i>
                    <div class="content">
                        <div class="title">Billing</div>
                        <div class="description">Enter billing information</div>
                    </div>
                </div>
                <div class="step">
                    <i class="info icon"></i>
                    <div class="content">
                        <div class="title">Confirm Order</div>
                    </div>
                </div>
            </div>
        </div>
        <div class="sixteen wide column">
            <c:url value="/j_spring_security_check" var="loginUrl"/>
            <form class="ui large form" action="${loginUrl}" method="post">


                <div class="ui stacked segment">
                    <div class="field">
                        <div class="ui left icon input">
                            <i class="user icon"></i>
                            <input type="text" name="j_username" placeholder="E-mail address">
                        </div>
                    </div>
                    <div class="field">
                        <div class="ui left icon input">
                            <i class="lock icon"></i>
                            <input type="password" name="j_password" placeholder="Password">
                        </div>
                    </div>
                    <button class="ui fluid large teal button">Submit</button>
                </div>
            </form>
        </div>
    </div>
</div>
</body>
</html>
