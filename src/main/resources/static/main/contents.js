
//로그인버튼
	function goLogin(){
		window.location.href = '/auth/login';
	}
	
//회원가입버튼
function goSignup(){
	window.location.href = '/auth/signup';
}

function hideAni () {    //안내 메세지 사라지게 하기
  const tooltip = $('.location_notice');
  tooltip && setTimeout(() => {
    
    tooltip.animate({
      
    }, 300, () => {
      tooltip.css('display', 'none');
    });
  }, 3000);
}

hideAni();

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



// floating banner (.qnb)
// 현재 스크롤이 이동한 높이를 배너의 높이에 적용
// https://m.blog.naver.com/PostView.nhn?blogId=pjh445&logNo=220261440747&proxyReferer=https:%2F%2Fwww.google.com%2F
// https://mastmanban.tistory.com/600


// #contents 이미지 슬라이드