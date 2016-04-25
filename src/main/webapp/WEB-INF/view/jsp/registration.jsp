<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="utf-8" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="cf" %>
<html>
<head>
    <title>Registration</title>

    <link href="<c:url value="/resources/dist/semantic.min.css" />" rel="stylesheet" type="text/css">
    <script src="<c:url value="/resources/vendor/js/jquery-2.2.1.js" />"></script>
    <script src="<c:url value="/resources/dist/semantic.min.js" />"></script>

    <style>
        #schedule label {
            min-width: 100px;
        }
    </style>

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
            <cf:form onsubmit="return checkForm(this)" class="ui large form" action="/addUser" method="POST"
                     modelAttribute="user">
            <br class="ui stacked segment">

            <div class="eight wide field">
                <div class="ui left icon input">
                    <i class="user icon"></i>
                    <cf:input type="email" path="email" placeholder="E-mail address" required="true"/>
                </div>
            </div>
            <div class="eight wide field">
                <div class="ui left icon input">
                    <i class="lock icon"></i>
                    <cf:input type="password" id="password" path="password" placeholder="Password" required="true"/>
                </div>
            </div>
            <div class="eight wide field">
                <div class="ui left icon input">
                    <i class="lock icon"></i>
                    <input type="password" id="confirm_password" placeholder="Confirm password" required="true"/>
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
                <cf:checkbox path="jobs" value="OPERATOR" id="truck_driver"/>
                <label for="truck_driver">Truck driver</label>
            </div>
            <div class="ui checkbox">
                <cf:checkbox path="jobs" value="DRIVER" id="crane_operator"/>
                <label for="crane_operator">Crane operator</label>
            </div>

            </br>
            </br>
            <div class="ui radio checkbox">
                <cf:radiobutton path="zeroHours" value="false" id="days"/>
                <label for="days"></label>
            </div>
            <div class="ui left icon input mini">
                <i class="lock icon"></i>
                <cf:input class="field" id="days-count" type="number" path="contractHours" placeholder="days"/>
            </div>

            <div class="ui radio checkbox">
                <cf:radiobutton path="zeroHours" value="true" id="0-days"/>
                <label for="0-days">0-days</label>
            </div>
            </br>
            <div id="schedule">
                <h3 class="header">Schedule</h3>
                <div class="inline field">
                    <label>Monday</label>
                    <cf:input id="monday" path="workSchedule.monday" type="number" placeholder="hours"/>
                </div>
                <div class="inline field">
                    <label>Tuesday</label>
                    <cf:input id="tuesday" path="workSchedule.tuesday" type="number" placeholder="hours"/>
                </div>
                <div class="inline field">
                    <label>Wednesday</label>
                    <cf:input id="wednesday" path="workSchedule.wednesday" type="number" placeholder="hours"/>
                </div>
                <div class="inline field">
                    <label>Thursday</label>
                    <cf:input id="thursday" path="workSchedule.thursday" type="number" placeholder="hours"/>
                </div>
                <div class="inline field">
                    <label>Friday</label>
                    <cf:input id="friday" path="workSchedule.friday" type="number" placeholder="hours"/>
                </div>
                <div class="inline field">
                    <label>Saturday</label>
                    <cf:input id="saturday" path="workSchedule.saturday" type="number" placeholder="hours"/>
                </div>
                <div class="inline field">
                    <label>Sunday</label>
                    <cf:input id="sunday" path="workSchedule.sunday" type="number" placeholder="hours"/>
                </div>
            </div>
            </br>

            <div class="inline field">
                <label>Payments</label>
                <div class="ui radio checkbox">
                    <cf:radiobutton path="fourWeekPayOff" value="false" id="mounthly"/>
                    <label for="mounthly">Mounthly</label>
                </div>
                <div class="ui radio checkbox">
                    <cf:radiobutton path="fourWeekPayOff" value="true" id="4-week"/>
                    <label for="4-week">4-week</label>
                </div>
            </div>

            <div class="eight wide field">
                <button class="ui fluid large teal button ">Submit</button>
            </div>

        </div>

        </cf:form>
    </div>
</div>


<script>
    console.log(1);

    function checkForm(form) {
        if (document.getElementById('password').value == document.getElementById('confirm_password').value) {
            var sum = 0;
            $('#schedule input').each(function () {
                if (parseInt($(this).val())) sum += parseInt($(this).val())
            })
            if (sum == $("#days-count").val()) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    $("#days").click(function () {
        $("#schedule").show();
        $("#days-count").removeClass("disabled");
    })
    $("#0-days").click(function () {
        $("#schedule").hide();
        $("#days-count").addClass("disabled");
        $("#days-count").val('0');
        $("#schedule input").val('');
    })
</script>
</body>
</html>
