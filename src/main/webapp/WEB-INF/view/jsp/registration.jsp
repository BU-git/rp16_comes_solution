<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="cf" uri="http://www.springframework.org/tags/form" %>

<t:layout title="Registration">
    <jsp:attribute name="head_area">

    </jsp:attribute>

    <jsp:attribute name="body_area">

        <div class="ui page dimmer" id="page-dimmer">
            <div class="content">
                <div class="ui loader"></div>
            </div>
        </div>
        <div class="ui center aligned grid main">

                <div class="sixteen wide column centered">
                    <cf:form id="registration-form" onsubmit="return checkForm(this)" class="ui large form"
                             action="/addUser" method="POST"
                             modelAttribute="user">
                        <br class="ui stacked segment">
                        <div class="field">
                            <div class="ui left icon input field">
                                <i class="user icon"></i>
                                <cf:input id="email" type="email" path="email" placeholder="E-mail address"
                                          required="true"/>
                                <cf:errors path="email" cssClass="ui negative message"/>
                            </div>
                            <div hidden id="email-error" class="ui negative message">This user already exist</div>
                        </div>

                        <div class="field">
                            <label>Password</label>
                            <div id="password-block" class="two fields">
                                <div class="ui left icon input field">
                                    <i class="lock icon"></i>
                                    <cf:input type="password" id="password" path="password" placeholder="Password"
                                              required="true"/>
                                    <cf:errors path="password" cssClass="ui negative message"/>
                                </div>
                                <div class="ui left icon input field">
                                    <i class="lock icon"></i>
                                    <input type="password" id="confirm_password" placeholder="Confirm password"
                                           required="true"/>
                                </div>
                            </div>
                            <div id="password-error" hidden class="ui negative message">Passwords are not equal</div>
                        </div>


                        <div class="field">
                            <label>Name</label>
                            <div class="two fields">
                                <div class="ui left icon input field">
                                    <i class="user icon"></i>
                                    <cf:input type="text" path="firstName" placeholder="First name" required="true"/>
                                    <cf:errors path="firstName" cssClass="ui negative message"/>
                                </div>
                                <div class="ui left icon input field">
                                    <i class="lock icon"></i>
                                    <cf:input type="text" path="lastName" placeholder="Last name" required="true"/>
                                    <cf:errors path="lastName" cssClass="ui negative message"/>
                                </div>
                            </div>
                            <div class="ui left icon input">
                                <i class="lock icon"></i>
                                <cf:input type="text" path="insertion" placeholder="Second name"/>
                            </div>
                        </div>
                        <div class="two fields">
                            <div class="ui left icon input field">
                                <i class="lock icon"></i>
                                <cf:input type="text" path="postalCode" placeholder="Postal code"/>
                            </div>
                            <div class="field">
                                <cf:select path="sex">
                                    <cf:option value="Male">Male</cf:option>
                                    <cf:option value="Female">Female</cf:option>
                                </cf:select>
                            </div>
                        </div>
                        <div class="ui checkbox">
                            <cf:checkbox path="jobs" value="OPERATOR" id="truck_driver"/>
                            <label for="truck_driver">Truck driver</label>
                        </div>
                        <div class="ui checkbox">
                            <cf:checkbox path="jobs" value="DRIVER" id="crane_operator"/>
                            <label for="crane_operator">Crane operator</label>
                        </div>

                        <br>
                        <br>
                        <div class="ui radio checkbox">
                            <cf:radiobutton path="zeroHours" value="false" id="days"/>
                            <label for="days"></label>
                        </div>
                        <div class="ui left icon input mini">
                            <i class="lock icon"></i>
                            <cf:input class="field" id="days-count" type="number" path="contractHours"
                                      placeholder="days"/>
                        </div>

                        <div class="ui radio checkbox">
                            <cf:radiobutton path="zeroHours" value="true" id="0-days"/>
                            <label for="0-days">0-days</label>

                        </div>
                        <div hidden id="calculation-error" class="ui negative message">Calculations must be equal</div>
                        <br>
                        <br>
                        <div id="schedule">
                            <h3 class="header">Schedule
                                <div id="help-icon" class="ui icon button">
                                    <i class="help icon"></i>
                                </div>
                            </h3>
                            <div class="two fields">
                                <div class="field">
                                    <label>Monday</label>
                                    <cf:input id="monday" path="workSchedule.monday" type="number" placeholder="hours"/>
                                </div>
                                <div class="field">
                                    <label>Tuesday</label>
                                    <cf:input id="tuesday" path="workSchedule.tuesday" type="number"
                                              placeholder="hours"/>
                                </div>
                            </div>
                            <div class="two fields">
                                <div class="field">
                                    <label>Wednesday</label>
                                    <cf:input id="wednesday" path="workSchedule.wednesday" type="number"
                                              placeholder="hours"/>
                                </div>
                                <div class="field">
                                    <label>Thursday</label>
                                    <cf:input id="thursday" path="workSchedule.thursday" type="number"
                                              placeholder="hours"/>
                                </div>
                            </div>
                            <div class="two fields">
                                <div class="field">
                                    <label>Friday</label>
                                    <cf:input id="friday" path="workSchedule.friday" type="number" placeholder="hours"/>
                                </div>
                                <div class="field">
                                    <label>Saturday</label>
                                    <cf:input id="saturday" path="workSchedule.saturday" type="number"
                                              placeholder="hours"/>
                                </div>
                            </div>
                            <div class="field">
                                <label>Sunday</label>
                                <cf:input id="sunday" path="workSchedule.sunday" type="number" placeholder="hours"/>
                            </div>
                        </div>
                        <br>

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

                        <div class="field">
                            <button id="registration-button" class="ui fluid large teal button ">Submit</button>
                        </div>
                    </cf:form>
                </div>
            </div>
        </div>



        <script>
            console.log(1);

            function checkForm(form) {
                var isExist = true;
                $.ajax({
                    url: 'rest/api/auth/exist',
                    type: 'GET',
                    async: false,
                    data: {email: $('#email').val()},
                    success: function () {
                        isExist = false;
                    },
                    error: function () {
                        $('#email-error').show();
                        $('html, body').animate({scrollTop: 0}, 500);
                        $("#page-dimmer").dimmer("hide");
                    }
                });
                if (document.getElementById('password').value == document.getElementById('confirm_password').value) {
                    if (isExist) {
                        return false;
                    }
                } else {
                    $('#password-error').show();
                    $('html, body').animate({scrollTop: 0}, 500);
                    $("#page-dimmer").dimmer("hide");
                    return false;
                }
                var sum = 0;
                $('#schedule input').each(function () {
                    if (parseInt($(this).val())) sum += parseInt($(this).val())
                });


                if (sum == $("#days-count").val()) {
                    return true;
                } else {
                    console.log('err');
                    $('#calculation-error').show();
                    $("#page-dimmer").dimmer("hide");
                    return false;
                }
            }

            $("#days").click(function () {
                $("#schedule").show();
                $("#days-count").removeClass("disabled");
            });

            $("#0-days").click(function () {
                $("#schedule").hide();
                $("#days-count").addClass("disabled");
                $("#days-count").val('0');
                $("#schedule input").val('');
            });

            var $registrationForm = $("#registration-form");
            $registrationForm.submit(function () {
                $("#page-dimmer").dimmer("show");
            });

            $("#help-icon").popup({
                on: "hover",
                title: "Schedule tip",
                content: "In this work schedule you fill in the number of hours you work each day"
            });

        </script>
    </jsp:attribute>
</t:layout>
