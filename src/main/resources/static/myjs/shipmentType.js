$(document).ready(function () {
    // 1 .Hide error variable
    $("#shipmentModeError").hide();
    $("#shipmentCodeError").hide();
    $("#enableShipmentError").hide();
    $("#shipmentGradeError").hide();
    $("#descriptionError").hide();

    //2 define error variable
    var shipmentModeError = false;
    var shipmentCodeError = false;
    var enableShipmentError = false;
    var shipmentGradeError = false;
    var descriptionError = false;

    function validate_shipmentMode() {
        var val = $("#shipmentMode").val();
        if (val == '') {
            $("#shipmentModeError").show();
            $("#shipmentModeError").html("<b>Please select shipment Mode</b>");
            $("#shipmentModeError").css('color', 'red');
            shipmentModeError = false;
        } else {
            $("#shipmentModeError").hide();
            shipmentModeError = true;
        }
        return shipmentModeError;
    }

    function validate_shipmentCode() {

        var val = $("#shipmentCode").val();
        var exp = /^[A-Z\-\s]{4,8}$/;
        if (val == '') {
            $("#shipmentCodeError").show();
            $("#shipmentCodeError").html("<b>Please Enter shipment Mode</b>");
            $("#shipmentCodeError").css('color', 'red');
            shipmentCodeError = false;
        } else if (!exp.test(val)) {
            $("#shipmentCodeError").show();
            $("#shipmentCodeError").html("<b>Shipment code must 4-8 chars</b>");
            $("#shipmentCodeError").css('color', 'red');
            shipmentCodeError = false;
        } else {
            var id = 0; // Register case
            if ($("#id").val() != undefined) {
                id = $("#id").val();
            }
            $.ajax({
                url: 'validate',
                data: {'code': val, "id": id},
                success: function (response) {
                    if (response != '') {
                        $("#shipmentCodeError").show();
                        $("#shipmentCodeError").html(response);
                        $("#shipmentCodeError").css('color', 'red');
                        shipmentCodeError = false;
                    } else {
                        $("#shipmentCodeError").hide();
                        shipmentCodeError = true;
                    }
                }

            });

        }

        return shipmentCodeError;
    }

    function validate_enableShipment() {
        var len = $("[name='enableShipment']:checked").length;
        if (len == 0) {
            $('#enableShipmentError').show();
            $('#enableShipmentError').html("<b>Please choose one option</b>");
            $('#enableShipmentError').css('color', 'red');
            enableShipmentError = false

        } else {
            $('#enableShipmentError').hide();
            enableShipmentError = true;
        }
        return enableShipmentError;
    }

    function validate_shipmentGrade() {
        var len = $("[name='shipmentGrade']:checked").length;
        if (len == 0) {
            $('#shipmentGradeError').show();
            $('#shipmentGradeError').html("<b>Please choose one option</b>");
            $('#shipmentGradeError').css('color', 'red');
            shipmentGradeError = false

        } else {
            $('#shipmentGradeError').hide();
            shipmentGradeError = true;
        }

        return shipmentGradeError;
    }

    function validate_description() {

        var val = $("#description").val();
        if (val == '') {
            $("#descriptionError").show();
            $("#descriptionError").html("<b>Please Enter Description</b>");
            $("#descriptionError").css('color', 'red');
            descriptionError = false;
        } else {
            $("#descriptionError").hide();
            descriptionError = true;
        }

        return descriptionError;
    }

    // 4. Link with event
    $("#shipmentMode").change(function () {
            validate_shipmentMode()
        }
    );
    $("#shipmentCode").keyup(function () {
            $(this).val($(this).val().toUpperCase());
            validate_shipmentCode()
        }
    );
    $("[name='enableShipment']").change(function () {
        validate_enableShipment()
    });
    $("[name='shipmentGrade']").change(function () {
        validate_shipmentGrade()
    });
    $("#description").change(function () {
        validate_description();
    });

    $("#shipmentTypeRegisterForm").submit(function () {
        validate_shipmentMode()
        validate_shipmentCode()
        validate_enableShipment()
        validate_shipmentGrade()
        validate_description()

        if (shipmentModeError && shipmentCodeError && enableShipmentError && shipmentGradeError && descriptionError) return true;
        return false;
    });
});