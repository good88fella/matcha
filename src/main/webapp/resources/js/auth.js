
$(document).ready(function(){
    var pattern = /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
    // при нажатии на кнопку "Создать личный кабинет"

    $("#reg").click(function(){
        $("#err_msg").html('');
        if ( (!($("#email_c").val() === '')) && (!($("#firstname_c").val() === '')) && (!($("#lastname_c").val() === '')) && (!($("#username_c").val() === '')) && (!($("#password_c").val() === ''))) {
            if ( !(pattern.test($("#email_c").val())) ) {
                // выводим сообщение о некорректности email и скрываем сообщение о том, что не все поля заполнены
                $("#err_msg").html('Неверно введён email');
            } else {
                $.ajax({
                    url : 'http://127.0.0.1:8081/account/registration',
                    datatype : 'json',
                    type : "post",
                    contentType : "application/json",
                    data : JSON.stringify({
                        login : $("#username_c").val(),
                        firstName : $("#firstname_c").val(),
                        lastName : $("#lastname_c").val(),
                        password : $("#password_c").val(),
                        matchingPassword : $("#passwordd_c").val(),
                        email : $("#email_c").val()
                    }),
                    success : function(data) {
                        if (data.success == true) {
                            location.href = 'http://127.0.0.1:8081/login.html'
                        } else {
                            alert(data.messages);
                        }
                    }
                });
            }
        } else {
            $("#err_msg").html('Заполнены не все поля');
        }
    });
    $("#authen").click(function(){
        $("#err_msg").html('');
        if ((!($("#username").val() === '')) && (!($("#password").val() === ''))) {
            $.ajax({
                url : 'http://127.0.0.1:8081/login',
                datatype : 'json',
                type : "post",
                contentType : "application/json",
                data : JSON.stringify({
                    username : $("#username").val(),
                    password : $("#password").val()
                }),
                success : function(data) {

                }
            });
        } else {
            $("#err_msg").html('Заполнены не все поля');
        }
    });
});
