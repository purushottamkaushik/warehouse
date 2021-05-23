$(document).ready(function () {
    // 1 . Hide Error section
    $("#userTypeError").hide()
    $("#userCodeError").hide()
    $("#userForError").hide()
    $("#userEmailError").hide()
    $("#userContactError").hide()
    $("#userIdTypeError").hide()
    $("#ifOtherError").hide()
    $("#userIdNumError").hide()
    $("#otherRow").hide()


    // 2. Declare error variable
    var userTypeError = false;
    var userCodeError = false;
    var userForError = false;
    var userEmailError = false;
    var userContactError = false;
    var userIdTypeError = false;
    var ifOtherError = false;
    var userIdNumError = false;

    //3 Create Function related to each field
    function validate_userType() {
        var length = $("[name='userType']:checked").length;
        if (length == 0) {
            $("#userTypeError").show();
            $("#userTypeError").html("***Please SELECT <b>User TYPE</b>");
            $("#userTypeError").css("color", "red");
            userTypeError = false
        } else {

            $("#userTypeError").hide();
            userTypeError = true;
            var elements = document.getElementsByName("userType");
            for (var i = 0; i < elements.length; i++) {
                if (elements[i].checked && elements[i].defaultValue == "Vendor") {
                    $("#userFor").val("Purchase")
                }
                if (elements[i].checked && elements[i].defaultValue == "Customer") {
                    $("#userFor").val("Sale")
                }
            }
            $("#userForError").hide();
        }

        return userTypeError;
    }

    function validate_userCode() {
        var value = $("#userCode").val();
        if (value == '') {
            $("#userCodeError").show();
            $("#userCodeError").html("***User Code required");
            $("#userCodeError").css("color", "red");
            userCodeError = false
        } else {
            var id = 0;
            if ($("#id").val() != undefined) {
                id = $("#id").val();
            }
            $.ajax({
                    url: "validatecode",
                    data: {"code": value, "id": id},
                    success: function (responseText) {
                        if (responseText != '') {
                            $("#userCodeError").show();
                            $("#userCodeError").html(responseText);
                            $("#userCodeError").css("color", "red");
                            userCodeError = false
                        } else {
                            $("#userCodeError").hide();
                            userCodeError = true;
                        }
                    }
                }
            );
        }
        return userCodeError;
    }

    function validate_userFor() {
        var value = $("#userFor").val();
        if (value == '') {
            $("#userForError").show();
            $("#userForError").html("**Please enter User For");
            $("#userForError").css("color", "red");
            userForError = false
        } else {
            $("#userForError").hide();
            userForError = true;
        }
        return userForError;
    }

    function validate_userEmail() {
        var value = $("#userEmail").val();
        var exp = /^(([^<>()[\]\\.,;:\s@"]+(\.[^<>()[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
        if (value == '') {
            $("#userEmailError").show();
            $("#userEmailError").html("***Please enter <b>Email</b>");
            $("#userEmailError").css("color", "red");
            userEmailError = false
        } else if (!exp.test(value)) {
            $("#userEmailError").show();
            $("#userEmailError").html("***Email is invalid");
            $("#userEmailError").css("color", "red");
            userEmailError = false
        } else {
            $("#userEmailError").hide();
            userEmailError = true
            var id = 0;
            if ($("#id").val() != undefined) {
                id = $("#id").val();
            }
            $.ajax({
                    url: "validateemail",
                    data: {"email": value, "id": id},
                    success: function (responseText) {
                        if (responseText != '') {
                            $("#userEmailError").show();
                            $("#userEmailError").html(responseText);
                            $("#userEmailError").css("color", "red");
                            userEmailError = false
                        } else {
                            $("#userEmailError").hide();
                            userEmailError = true;
                        }
                    }
                }
            );
        }

        return userEmailError;
    }

    function validate_userContact() {
        var value = $("#userContact").val();
        var exp = /^[0-9]{10}$/;
        if (value == '') {
            $("#userContactError").show();
            $("#userContactError").html("Please enter <b>Contact number</b>");
            $("#userContactError").css("color", "red");
            userContactError = false
        } else if (!exp.test(value)) {
            $("#userContactError").show();
            $("#userContactError").html("Please enter Valid <b>Contact number</b>");
            $("#userContactError").css("color", "red");
            userContactError = false
        } else {
            $("#userContactError").hide();
            userContactError = true
        }
        return userContactError;
    }

    function validate_userIdType() {
        var value = $("#userIdType").val();
        if (value == '') {
            $("#userIdTypeError").show();
            $("#userIdTypeError").html("Please select USER Id");
            $("#userIdTypeError").css("color", "red");
            userIdTypeError = false;
        } else {
            $("#userIdTypeError").hide();
            userIdTypeError = true;
            if (value == "OTHER") {
                $("#otherRow").show();
            } else {
                $("#otherRow").hide();
            }
        }
        return userIdTypeError;
    }

    function validate_userIfOther() {
        var value = $("#ifOther").val();
        if (value == '') {
            $("ifOtherError").show();
            $("ifOtherError").html("Please enter ID");
            $("ifOtherError").css("color", "red");
            ifOtherError = false;
        } else {
            $("ifOtherError").hide();
            ifOtherError = true;
        }
        return ifOtherError;
    }

    function validate_userIdNum() {
        var value = $("#userIdNum").val();
        // var exp = /^[A-Za-z0-9\.]{8,30}@[].[A-Za-z0-9]*.[a-z]{4,10}$/;
        if (value == '') {
            $("#userIdNumError").show();
            $("#userIdNumError").html("***Please enter <b>User Id</b>");
            $("#userIdNumError").css("color", "red");
            userIdNumError = false
        } else {
            $("userIdNumError").hide()
            userIdNumError = true
            var id = 0;
            if ($("#id").val() != undefined) {
                id = $("#id").val();
            }
            $.ajax({
                    url: "validateidnum",
                    data: {"idNumber": value, "id": id},
                    success: function (responseText) {
                        if (responseText != '') {
                            $("#userIdNumError").show();
                            $("#userIdNumError").html(responseText);
                            $("#userIdNumError").css("color", "red");
                            userIdNumError = false
                        } else {
                            $("#userIdNumError").hide();
                            userIdNumError = true;
                        }
                    }
                }
            );
        }

        return userIdNumError;
    }

    // Link to action event

    $("[name='userType']").change(function () {
        validate_userType()
        validate_userFor()
    });
    $("#userCode").keyup(function () {
        validate_userCode()
    });
    // $("#userFor").keyup(function() {validate_userCode()});
    $("#userEmail").keyup(function () {
        validate_userEmail()
    });
    $("#userContact").keyup(function () {
        validate_userContact()
    });
    $("#userIdType").change(function () {
        validate_userIdType()
    });
    $("#ifOther").keyup(function () {
        validate_userIfOther()
    });
    $("#userIdNum").keyup(function () {
        validate_userIdNum()
    });

    // on Form submit validate all
    $("#whUserTypeRegisterForm").submit(function () {
            validate_userType()
            validate_userCode()
            validate_userFor()
            validate_userEmail()
            validate_userContact()
            validate_userIdType()
            validate_userIfOther()
            validate_userIdNum()

            if ($("#userIdType").val() == "OTHER" &&
                (userTypeError && userCodeError &&
                    userForError && userEmailError && userContactError &&
                    userIdTypeError && ifOtherError && userIdNumError)) {
                return true
            }
            if ($("#userIdType").val() != "OTHER" &&
                (userTypeError && userCodeError &&
                    userForError && userEmailError && userContactError &&
                    userIdTypeError && userIdNumError)) {
                return true
            }
            return false;
        }
    );
});