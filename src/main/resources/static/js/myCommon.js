var text_pw2 = $('.field_repw input');
    text_pw2.focus(function(){
        $('.field_repw .txt_guide').css('display', 'block');
    });
    text_pw2.blur(function(){      
        pw2_check();
    });




    var text_pw3 = $('.field_nowpw input');
    text_pw3.focus(function(){
        $('.field_nowpw .txt_guide').css('display', 'block');
    });
    text_pw3.blur(function(){
        pw3_check();
    })
