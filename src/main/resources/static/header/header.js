//로그인버튼
function goLogin(){
	window.location.href = '/auth/login';
}
	
//회원가입버튼
function goSignup(){
	window.location.href = '/auth/signup';
}


$(document).ready(function(){  
//마우스 hover 하면 안내메세지 보이게하기
	const hover = document.querySelector('.location_notice');
	
	const btn_hover = document.querySelector('.btn_location');
	
	hover.onmouseover = function() {
	    hover.style.display = 'block';
	}
	hover.onmouseout = function(){
	    hover.style.display = 'none';
	}
	btn_hover.onmouseover = function() {
	    hover.style.display = 'block';
	}
	btn_hover.onmouseout = function(){
	    hover.style.display = 'none';
	}
	
	
	//카테고리 선택시 해당 카테고리검색
	$('.menu .current .submenu-right li.current').on('click', function() {
    
    	if (!$(this).hasClass('notcurrent')) {
        var topText = $(this).closest('li.current').find('.tit .txt').text().trim();
    	var keyword = topText.split('·')[0].trim();
    
    	var url = '/wewalk/search?keyword=c_' + keyword;
    	window.location.href = url;
    	}
	});
	
	
	
});