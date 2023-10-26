//로그인버튼
function goLogin(){
	window.location.href = '/auth/login';
}
	
//회원가입버튼
function goSignup(){
	window.location.href = '/auth/signup';
}

function godesktop(){
alert('dd');
//	window.location.href = '/wewalk/search?keyword=c_데스크탑';
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
});