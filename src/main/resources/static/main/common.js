window.addEventListener('DOMContentLoaded',function(){


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
    // gnb 상단 고정 끝 --


    // gnb_search
    // (1) btn_delete 클릭 > value 값 초기화
    const btnD = document.querySelector('.btn_delete');
    const inpSearch = document.querySelector('.inp_search');

    btnD.onclick = function () {
        inpSearch.value = '';
    }

    // (2) 입력 창 focus > 배경 #fff 변경, focus를 잃으면 원래대로.
    // transition 0.3초 추가
    inpSearch.addEventListener('focus', function(changeBg) {
        this.style.backgroundColor = '#fff';
    }, true);
    inpSearch.addEventListener('blur', function(changeBg) {
        this.style.backgroundColor = '';
    }, true);


    var search = document.getElementsByClassName("btn_search");
    search[0].addEventListener("click", function () {
		document.getElementById("searchForm").submit();
	});

	
	
	
	//상품 이미지슬라이드
    var imgs; // 사용할 이미지들
	var img_count; // 카운트 변수
	
	imgs = $('.list_goods_ul li'); // 이미지는 .list_goods_ul 안에 있는 li 태그
	img_count = imgs.length; // slide ul의 자식, 즉 li의 갯수 = 이미지의 갯수
	
	var currentIndex = 0; // To keep track of the currently displayed items
	updateDisplay(); // Initialize the display
	
	$('.bx-prev').click(function () {
	    slide_left();
	});
	
	$('.bx-next').click(function () {
	    slide_right();
	});
	
	function slide_left() {
	    if (currentIndex > 0) {
	        currentIndex--;
	        updateDisplay();
	    }
	}
	
	function slide_right() {
	    if (currentIndex < img_count - 4) {
	        currentIndex++;
	        updateDisplay();
	    }
	}
	
	function updateDisplay() {
	    var leftPosition = -currentIndex * 267; // Assuming each item is 267px wide (249px width + 18px margin-right)
	    $('.list_goods_ul').css("left", leftPosition + "px");
	}


});