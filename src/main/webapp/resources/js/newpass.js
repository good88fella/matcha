$(document).ready(function(){
    $("#newpass_1").click(function(){
        $("#err_msg").html('');
        if ((!($("#email_n").val() === '')) ) {
            $.ajax({
                url : '/api/myrequest',
                datatype : 'json',
                type : "post",
                contentType : "application/json",
                data : JSON.stringify({
                    email : $("#email_n").val()
                }),
                success : function() {
                    document.getElementById('newpass_1').style.display = 'none';
                    document.getElementById('newpass_2').style.display = 'inline-block';
                    document.getElementById('n1').style.display = 'none';
                    document.getElementById('n2').style.display = 'block';
                }
            });
        } else {
            $("#err_msg").html('Заполнены не все поля');
        }
    });
    $("#newpass_2").click(function(){
        $("#err_msg").html('');
        if ((!($("#code_n").val() === '')) ) {
            $.ajax({
                url : '/api/myrequest',
                datatype : 'json',
                type : "post",
                contentType : "application/json",
                data : JSON.stringify({
                    code : $("#code_n").val()
                }),
                success : function(data) {
                    if (data.success == true) {
                        document.getElementById('newpass_2').style.display = 'none';
                        document.getElementById('newpass_3').style.display = 'inline-block';
                        document.getElementById('n3').style.display = 'none';
                        document.getElementById('n3').style.display = 'block';
                        document.getElementById('n3_3').style.display = 'block';
                    } else {
                        alert(data.messages);
                    }
                }
            });
        } else {
            $("#err_msg").html('Заполнены не все поля');
        }
    });
    $("#newpass_3").click(function(){
        $("#err_msg").html('');
        if ((!($("#password_n").val() === '')) && (!($("#passwordd_n").val() === ''))) {
            $.ajax({
                url : '/api/myrequest',
                datatype : 'json',
                type : "post",
                contentType : "application/json",
                data : JSON.stringify({
                    password : $("#password_n").val(),
                    matchingPassword : $("#passwordd_n").val()
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