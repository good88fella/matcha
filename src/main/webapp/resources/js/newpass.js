$(document).ready(function(){
    $("#newpass_2").click(function(){
        $("#err_msg").html('');
        $.ajax({
            url : 'http://127.0.0.1:8081/account/sendcode',
            type : "post",
            success : function(data) {
                if (data.success == true) {
                    document.getElementById('newpass_2').style.display = 'none';
                    document.getElementById('newpass_3').style.display = 'inline-block';
                    document.getElementById('n2').style.display = 'block';
                    document.getElementById('n3').style.display = 'block';
                } else {
                    alert(data.messages);
                }
            }
        });
    });
    $("#newpass_3").click(function(){
        $("#err_msg").html('');
        if ((!($("#code_n").val() === '')) && (!($("#password_n").val() === ''))) {
            $.ajax({
                url : 'http://127.0.0.1:8081/account/restorepassword',
                datatype : 'json',
                type : "post",
                contentType : "application/json",
                data : JSON.stringify({
                    code : $("#code_n").val(),
                    newPassword : $("#password_n").val()
                }),
                success : function(data) {
                    if (data.success == true) {
                        location.href = 'http://127.0.0.1:8081/login.html'
                    } else {
                        alert(data.messages);
                    }
                }
            });
        } else {
            $("#err_msg").html('Заполнены не все поля');
        }
    });
})