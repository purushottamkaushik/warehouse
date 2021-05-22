$(document).ready(function () {
        $("#uomTypeError").hide();
        $("#uomModelError").hide();
        $("#descriptionError").hide();

        var uomTypeError = false;
        var uomModelError = false;
        var descriptionError = false;

        function validate_uomType() {
            var val = $("#uomType").val();
            if (val == '') {
                $("#uomTypeError").show();
                $("#uomTypeError").html("Please select <b>UOM Type</b>");
                $("#uomTypeError").css("color", "red");
                uomTypeError = false;
            } else {
                $("#uomTypeError").hide();
                uomTypeError = true;

            }
            return uomTypeError;
        }

        function validate_uomModel() {
            var val = $("#uomModel").val();
            var exp = /^[A-Z\-\s]{8,12}$/
            if (val == '') {
                $("#uomModelError").show();
                $("#uomModelError").html("Please enter <b> UOM Model</b>");
                $("#uomModelError").css("color", "red");
                uomModelError = false;
            } else if (!exp.test(val)) {
                $("#uomModelError").show();
                $("#uomModelError").html("UOM model must be 8-12 chars");
                $("#uomModelError").css("color", "red");
                uomModelError = false;
            } else {
                var id = 0;
                if ($("#uomId").val() !== undefined) {
                    id = $("#uomId").val();
                }
                $.ajax({
                        url: 'validate',
                        data: {"uomModel": val, "uomId": id},
                        success: function (txt) {
                            if (txt != '') {

                                $("#uomModelError").show();
                                $("#uomModelError").html(txt);
                                $("#uomModelError").css("color", "red");
                                uomModelError = false;
                            } else {
                                $("#uomModelError").hide();
                                uomModelError = true;
                            }
                        }
                    }
                );
            }
            return uomModelError;
        }

        function validate_description() {
            var val = $("#description").val();
            if (val == '') {
                $("#descriptionError").show();
                $("#descriptionError").html("Please provide <b> Description</b>");
                $("#descriptionError").css("color", "red");
                descriptionError = false;
            } else {
                $("#descriptionError").hide();
                descriptionError = true;
            }
            return descriptionError;
        }

        // Link to action event

        $("#uomType").change(function () {
            validate_uomType()
        });

        $("#uomModel").keyup(function () {
            $(this).val($(this).val().toUpperCase());
            validate_uomModel()
        });
        $("#description").keyup(function () {
            validate_description()
        });

        $("#uomRegisterForm").submit(function () {
            validate_uomType()
            validate_uomModel()
            validate_description()
            if (uomTypeError && uomModelError && descriptionError) {
                return true
            }
            return false
        });
    }
);