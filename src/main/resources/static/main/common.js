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


	//검색버튼 활성화
    var search = document.getElementsByClassName("btn_search");
    search[0].addEventListener("click", function () {
		document.getElementById("searchForm").submit();
	});

	
	
	//최근등록된상품 이미지슬라이드
    var imgs_roll1; // 사용할 이미지들
	var img_count_roll1; // 카운트 변수
	
	imgs_roll1 = $('.rollmenu1 .list_goods_ul li'); // 이미지는 .list_goods_ul 안에 있는 li 태그
	img_count_roll1 = imgs_roll1.length; // slide ul의 자식, 즉 li의 갯수 = 이미지의 갯수
	
	var currentIndex_roll1 = 0;
	updateDisplay_roll1();
	
	$('.rollmenu1 .bx-prev').click(function () {
	    slide_left_roll1();
	});
	
	$('.rollmenu1 .bx-next').click(function () {
	    slide_right_roll1();
	});
	
	function slide_left_roll1() {
	    if (currentIndex_roll1 > 0) {
	        currentIndex_roll1--;
	        updateDisplay_roll1();
	    }
	}
	
	function slide_right_roll1() {
	    if (currentIndex_roll1 < img_count_roll1 - 4) {
	        currentIndex_roll1++;
	        updateDisplay_roll1();
	    }
	}
	
	function updateDisplay_roll1() {
	
	    var leftPosition = -currentIndex_roll1 * 267;
	    $('.rollmenu1 .list_goods_ul').css("left", leftPosition + "px");
	    
	    if (currentIndex_roll1 === img_count_roll1 - 4) {
			$('.rollmenu1 .bx-next').hide();
   		}else{
   			$('.rollmenu1 .bx-next').show();
   		}
   		
   		if (currentIndex_roll1 === 0) {
			$('.rollmenu1 .bx-prev').hide();
   		}else{
   			$('.rollmenu1 .bx-prev').show();
   		}
	    
	}

	
	
	//많이팔린상품 이미지슬라이드
    var imgs_roll2; // 사용할 이미지들
	var img_count_roll2; // 카운트 변수
	
	imgs_roll2 = $('.rollmenu2 .list_goods_ul li'); // 이미지는 .list_goods_ul 안에 있는 li 태그
	img_count_roll2 = imgs_roll2.length; // slide ul의 자식, 즉 li의 갯수 = 이미지의 갯수
	
	var currentIndex_roll2 = 0;
	updateDisplay_roll2();
	
	$('.rollmenu2 .bx-prev').click(function () {
	    slide_left_roll2();
	});
	
	$('.rollmenu2 .bx-next').click(function () {
	    slide_right_roll2();
	});
	
	function slide_left_roll2() {
	    if (currentIndex_roll2 > 0) {
	        currentIndex_roll2--;
	        updateDisplay_roll2();
	    }
	}
	
	function slide_right_roll2() {
	    if (currentIndex_roll2 < img_count_roll2 - 4) {
	        currentIndex_roll2++;
	        updateDisplay_roll2();
	    }
	}
	
	function updateDisplay_roll2() {
	
	    var leftPosition = -currentIndex_roll2 * 267;
	    $('.rollmenu2 .list_goods_ul').css("left", leftPosition + "px");
	    
	    if (currentIndex_roll2 === img_count_roll2 - 4) {
			$('.rollmenu2 .bx-next').hide();
   		}else{
   			$('.rollmenu2 .bx-next').show();
   		}
   		
   		if (currentIndex_roll2 === 0) {
			$('.rollmenu2 .bx-prev').hide();
   		}else{
   			$('.rollmenu2 .bx-prev').show();
   		}
	    
	}


});