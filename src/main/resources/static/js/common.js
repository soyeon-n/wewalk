window.addEventListener('DOMContentLoaded',function(){

    

    $('.top_event_close').click(function(){
        $("#top_event").slideUp(200)
    });


    const gnb = document.querySelector('.gnb');
    const gnbTopOffset = gnb.offsetTop;
    window.addEventListener('scroll', e =>{

        if (window.pageYOffset >= gnbTopOffset) {
            gnb.style.position = 'fixed';
            gnb.style.top = 0;
            gnb.style.left = 0;
            gnb.style.right = 0;
        } else {
            gnb.style.position = '';
            gnb.style.top = '';
        }
    });


    const btnD = document.querySelector('.btn_delete');
    const inpSearch = document.querySelector('.inp_search');

    btnD.onclick = function () {
        inpSearch.value = '';
    }


    inpSearch.addEventListener('focus', function(changeBg) {
        this.style.backgroundColor = '#fff';
    }, true);
    inpSearch.addEventListener('blur', function(changeBg) {
        this.style.backgroundColor = '';
    }, true);


    $(window).scroll(function(){
        var scrollTop = $(document).scrollTop();
        if (scrollTop < 70) {
         scrollTop = 70;
        }
        $(".qnb").stop();
        $(".qnb").animate( { "top" : scrollTop });
        });

    


});