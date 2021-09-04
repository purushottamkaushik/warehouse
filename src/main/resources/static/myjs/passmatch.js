$(document).ready(function () {
        $('passwordError').hide();
        var newPassword;
        var repeatPassword;

        var passwordError = false ;

        function validatePassword() {

            newPassword = $('#pass1').val();
            repeatPassword = $('#pass2').val();

            if (newPassword == repeatPassword) {
                $('#passwordError').hide()
                passwordError = true;

            } else {
                $('#passwordError').show()
                $('#passwordError').html("Passwords not matched ")
                $('#passwordError').css('color','red')
                passwordError = false
            }

        }

        $('#pass2').focusout(function () {
            validatePassword()
        });

        $('#passwordUpdateForm').submit(function () {
            validatePassword()
            if (passwordError) {
                return true
            }
            else {
                return false
            }
        })

    }
);