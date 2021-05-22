$(document).ready(function () {

    //1 Hide error section by default
    $("#orderModeError").hide()
    $("#orderCodeError").hide()
    $("#orderTypeError").hide()
    $("#orderAcceptError").hide()
    $("#descriptionError").hide()

    // 2. Declare error variable

    var orderModeError = false
    var orderCodeError = false
    var orderTypeError = false
    var orderAcceptError = false
    var descriptionError = false

    // 3. Validate function  for all fields
    function validate_orderMode() {
        var value = $("[name='orderMode']:checked").length;
        if (value == 0) {
            $("#orderModeError").show();
            $("#orderModeError").html("Please select <b>Order Mode</b>");
            $("#orderModeError").css("color", "red");
            orderModeError = false;
        } else {
            $("#orderModeError").hide();
            orderModeError = true;
        }
        return orderModeError;
    }

    function validate_orderCode() {
        var value = $("#orderCode").val();
        if (value == "") {
            $("#orderCodeError").show();
            $("#orderCodeError").html("Please Enter  <b>Order Code</b>");
            $("#orderCodeError").css("color", "red");
            orderCodeError = false;
        } else {

            var id = 0; // Register purpose
            if ($("#id").val() != undefined) {
                id = $("#id").val();
            }
            $.ajax({
                url: "validate",
                data: {"id": id, "code": value},
                success: function (responseText) {
                    if (responseText != '') {
                        $("#orderCodeError").show();
                        $("#orderCodeError").html(responseText);
                        $("#orderCodeError").css("color", "red");
                        orderCodeError = false;
                    } else {
                        $("#orderCodeError").hide();
                        orderCodeError = true;
                    }
                }
            });
        }
        return orderCodeError;
    }

    function validate_orderType() {
        var value = $("[name='orderType']").val();
        if (value == 0) {
            $("#orderTypeError").show();
            $("#orderTypeError").html("***Please Select <b>Order Type</b>");
            $("#orderTypeError").css("color", "red");
            orderTypeError = false;
        } else {
            $("#orderTypeError").hide();
            orderTypeError = true;
        }
        return orderTypeError;
    }

    function validate_orderAccept() {
        var value = $("[name='orderAccept']:checked").length;

        if (value == 0) {
            $("#orderAcceptError").show();
            $("#orderAcceptError").html("Please select <b>Order Accept</b>");
            $("#orderAcceptError").css("color", "red");
            orderAcceptError = false;
        } else {
            $("#orderAcceptError").hide();
            orderAcceptError = true;
        }

        return orderAcceptError;
    }

    function validate_description() {
        var value = $("#description").val();
        if (value == "") {
            $("#descriptionError").show();
            $("#descriptionError").html("Please Enter  <b>Description</b>");
            $("#descriptionError").css("color", "red");
            descriptionError = false;
        } else {
            $("#descriptionError").hide();
            descriptionError = true;
        }

        return descriptionError;
    }

    // Link with action event
    $("[name='orderMode']").change(function () {
        validate_orderMode();
    }); // radio button
    $("#orderCode").keyup(function () {
        validate_orderCode()
    }); // text field // valid
    $("#orderType").change(function () {
        validate_orderType()
    }); // dropdown
    $("[name='orderAccept']").change(function () {
        validate_orderAccept()
    }); // order accept checkbox
    $("#description").keyup(function () {
        validate_description()
    }); // text area // valid

    $("#orderMethodRegisterForm").submit(function () {
        validate_orderMode()
        validate_orderCode()
        validate_orderType()
        validate_orderAccept()
        validate_description()
        if (orderModeError && orderCodeError && orderTypeError && orderAcceptError && descriptionError) return true
        else return false;
    });

});