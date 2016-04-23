<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="utf-8"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="cf"%>
<html>
<head>
    <title>Registration</title>

    <link href="<c:url value="/resources/dist/semantic.min.css" />" rel="stylesheet" type="text/css">
    <script src="<c:url value="/resources/dist/semantic.min.js" />"></script>

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
        <div class="sixteen wide column centered">
            <cf:form onsubmit="return checkForm(this)" class="ui large form" action="/addUser" method="POST" modelAttribute="user">
                <div class="ui stacked segment">

                    <div class="eight wide field">
                        <div class="ui left icon input">
                            <i class="user icon"></i>
                            <cf:input type="email" path="email" placeholder="E-mail address" required="true" />
                        </div>
                    </div>
                    <div class="eight wide field">
                        <div class="ui left icon input">
                            <i class="lock icon"></i>
                            <cf:input type="password" id="password" path="password" placeholder="Password" required="true" />
                        </div>
                    </div>
                    <div class="eight wide field">
                        <div class="ui left icon input">
                            <i class="lock icon"></i>
                            <input type="password" id="confirm_password" placeholder="Confirm password" required="true" />
                        </div>
                    </div>
                    <div class="eight wide field">
                        <div class="ui left icon input">
                            <i class="user icon"></i>
                            <cf:input type="text" path="firstName" placeholder="First name"/>
                        </div>
                    </div>
                    <div class="eight wide field">
                        <div class="ui left icon input">
                            <i class="lock icon"></i>
                            <cf:input type="text" path="lastName" placeholder="Last name"/>
                        </div>
                    </div>
                    <div class="eight wide field">
                        <div class="ui left icon input">
                            <i class="lock icon"></i>
                            <cf:input type="text" path="insertion" placeholder="Second name"/>
                        </div>
                    </div>
                    <div class="eight wide field">
                        <div class="ui left icon input">
                            <i class="lock icon"></i>
                            <cf:input type="text" path="postalCode" placeholder="Postal code"/>
                        </div>
                    </div>
                    <div class="eight wide field">
                        <cf:select path="sex">
                            <cf:option value="Male">Male</cf:option>
                            <cf:option value="Female">Female</cf:option>
                        </cf:select>
                    </div>
                    <div class="ui checkbox">
                        <input type="checkbox" name="example">
                        <label>Truck driver</label>
                    </div>
                    <div class="ui checkbox">
                        <input type="checkbox" name="example">
                        <label>Crane operator</label>
                    </div>

                    <div class="eight wide field">
                        <button class="ui fluid large teal button ">Submit</button>
                    </div>

                </div>

            </cf:form>
        </div>
    </div>
</div>

<script>
    function checkForm(form) {
        if (document.getElementById('password').value == document.getElementById('confirm_password').value) {
            console.log(1);
            return true;
        } else {
            console.log(2);
            return false;
        }

    }
</script>
</body>
</html>
