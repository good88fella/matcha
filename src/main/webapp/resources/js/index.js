$(document).ready(function() {
    $("#mobout").click(function(){
        $.ajax({
            url : 'http://127.0.0.1:8081/logout',
            type : "post",
            success : function(data) {
            }
        });
    });
    $("#logout").click(function(){
            $.ajax({
            url : 'http://127.0.0.1:8081/logout',
            type : "post",
            success : function(data) {
            }
        });
    });
    $('#but_inet').on('click', function(e) {
        document.getElementById('detailbuttons').style.display = 'none';
        document.getElementById('detailinform').style.display = 'block';
        document.getElementById('back_detail_1').style.display = 'inline-block';
    });
    $('.scrollup').click(function() {
    // переместиться в верхнюю часть страницы
        $("html, body").animate({
        scrollTop:0
        },500);
    });
    $(window).scroll(function() {
        // если пользователь прокрутил страницу более чем на 200px
        if ($(this).scrollTop()>200) {
        // то сделать кнопку scrollup видимой
            $('.scrollup').fadeIn();
        }
        // иначе скрыть кнопку scrollup
        else {
            $('.scrollup').fadeOut();
        }
    });
    $('.logout').on('click', function(e) {
        $.ajax({
            url: 'php/logout.php',
            type: 'POST',
            success: function(data){
                location.href = 'http://tech.sysat.ru/d.vyazin/skd_lk/';
            }
        });
    });
});

$(window).resize(function(){
    if (Number($(window).width()) > 765) {
        var check1 = document.getElementById('back_detail_1');
        check1.click()
        $('#barnav').show();
    } else {
        var check1 = document.getElementById('back_detail_1');
        check1.click();
        var sat = document.getElementsByClassName('butsatis');
        for(var i=0; i<sat.length; i++)sat[i].style.display='none';
    }
});

function blocknone(str) {
    $('#' + str).fadeOut(250, function(){
        $('#' + str).fadeIn(1000);
    });
    //document.getElementById(str).style.display = 'block';
    if(str == 'chats'){
        var height = Number($(window).height());
        var result = $('#paytable');
        $.ajax({
            url: 'php/payment.php',
            type: 'POST',
            beforeSend: function() {
                result.html("<img src='css/Loading.gif'>"); 
            },
            success: function(data){

            }
        });
    }
    if(str == 'inform'){
        var result = $('#inform');
        $.ajax({
            url: 'php/client_info.php',
            type: 'POST',
            beforeSend: function() {
                result.html("<img src='css/Loading.gif'>"); 
            },
            success: function(data){
                result.html(data);
            }
        });
    }
    if(str == 'find'){
        var result = $('#tariff');
        $.ajax({
            url: 'php/tariff_skd.php',
            type: 'POST',
            beforeSend: function() {
                result.html("<img src='css/Loading.gif'>"); 
            },
            success: function(data){
                result.html(data);
            }
        });
    }
}
