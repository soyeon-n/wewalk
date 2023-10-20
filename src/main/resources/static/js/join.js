$(document).ready(function(){
	
	// userName 유효성 검사
    $('#userNameInput').on('blur', userName_check);

    // password1 유효성 검사
    $('#password1').on('blur', pw_check);

    // password2 유효성 검사
    $('#password2').on('blur', pw2_check);
    
    $('#checkUserNameBtn').on('click', function(e){
        e.preventDefault();
        
        const userName = $('#userNameInput').val();
        if(!userName) {
            $('#userNameValidationMessage').css('color', '#b3130b').text('닉네임을 입력하세요.');
            return;
        }
        
        $.ajax({
            url: '/auth/checkUserName',
            type: 'GET',
            data: { userName: userName },
            success: function(response) {
                if(response.status === 'exists') {
                    $('#userNameValidationMessage').css('color', '#b3130b').text('이미 존재하는 닉네임입니다.');
                } else {
                    $('#userNameValidationMessage').css('color', '#0f851a').text('사용 가능한 닉네임입니다.');
                }
            },
            error: function() {
                $('#userNameValidationMessage').css('color', '#b3130b').text('닉네임 검사 중 오류가 발생했습니다.');
            }
        });
    });
});

function userName_check(){
    var userName = $("#userNameInput").val();
    var userName_txt_case1 = $('.userName_txt_case1');
    
    var num = /[0-9]/;
    var eng = /[a-zA-Z]/;
    var notNumOrEng = /[^a-zA-Z0-9]/;
    
    if(userName.length >= 6 && notNumOrEng.test(userName) == 0 && eng.test(userName) == 1) {
        $(".userName_txt_case1").css('color', '#0f851a');
    } else {
        $(".userName_txt_case1").css('color', '#b3130b');
    }
}

function pw_check(){

	var pw1 = $("#password1").val();                   // 변수 pw에 pw값 대입
	var password1_txt_case1 = $('.password1_txt_case1');
	var password1_txt_case2 = $('.password1_txt_case2');

	
    var num = /[0-9]/;
    var engLower = /[a-z]/;
    var engUpper = /[A-Z]/;
    var spe = /[~!@#$%^&*()_+|<>?:{}]/;
    
    if(pw1.length < 6){                                  //pw의 길이가 6 미만일 때
    	password1_txt_case1.css('color', '#b3130b'); // 빨간색
    }

    if(pw1.length >= 6){                                  //pw의 길이가 6 이상일 때
    	password1_txt_case1.css('color', '#0f851a'); // 초록색 
    }

    if(num.test(pw1) == 0 || engLower.test(pw1) == 0 || engUpper.test(pw1) == 0 || spe.test(pw1) == 0){    // pw의 숫자가 없거나 , 소문자가 없거나, 대문자가 없거나, 특수문자가 없을경우 실패
    	password1_txt_case2.css('color', '#b3130b'); // 빨간색
    }            

    if(num.test(pw1) == 1 && engLower.test(pw1) == 1 && engUpper.test(pw1) == 1 && spe.test(pw1) == 1){ // pw의 숫자,영어,특수문자가 1개이상씩 있을경우 성공
    	password1_txt_case2.css('color', '#0f851a'); // 초록색
    }

}

function pw2_check(){
	var password1 = $('#password1').val();
	var password2 = $('#password2').val();
	var password2_txt_case1 = $('.password2_txt_case1');

    if ( password1 != '' && password2 == '' ) {          //둘다 빈칸일 경우 아무것도 하지 않음
        null;
    } else if (password1 != "" || password2 != "") {     // 빈칸이 아닐 경우
        if (password1 == password2) {                    // 비교해서 같으면
        	password2_txt_case1.css('color', '#0f851a'); // 초록색                  
        
        } else {        // 비교해서 같지 않으면
        	password2_txt_case1.css('color', '#b3130b'); // 빨간색                          
        }
    }
}

// 이메일 유효성 검사 함수
const isValidEmail = (email) => {
    const regex = /^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,4}$/;
    return regex.test(email);
};

// 화살표 함수
const sendIt = () => {
	
    let f = document.myForm;

    let fields = [
        { field: f.userName, message: "계정명을 입력하세요." },
        { field: f.password1, message: "비밀번호를 입력하세요." },
        { field: f.password2, message: "비밀번호 확인을 입력하세요." },
        { field: f.email, message: "이메일을 입력하세요." },
        { field: f.name, message: "이름을 입력하세요." },
        { field: f.postcode, message: "우편번호를 입력하세요." },
        { field: f.address, message: "주소를 입력하세요." },
        { field: f.detailAddress, message: "상세 주소를 입력하세요." },
        { field: f.tel, message: "전화번호를 입력하세요." },
        { field: f.birthYear, message: "생년을 입력하세요." },
        { field: f.birthMonth, message: "생월을 입력하세요." },
        { field: f.birthDay, message: "생일을 입력하세요." }
    ];

    for (let i = 0; i < fields.length; i++) {
        let str = fields[i].field.value.trim();
        if (!str) {
            alert(`\n${fields[i].message}`);
            fields[i].field.focus();
            return false;
        }
        fields[i].field.value = str;
    }

    if (!isValidEmail(f.email.value)) {
        alert("\n정상적인 E-Mail을 입력하세요.");
        f.email.focus();
        return false;
    }

    if (f.password1.value !== f.password2.value) {
        alert("\n비밀번호가 일치하지 않습니다.");
        f.password2.focus();
        return false;
    }

    return true;
}

// 화살표 함수
const sendOAuth = () => {
	
    let f = document.myForm;

    let fields = [
        { field: f.userName, message: "계정명을 입력하세요." },
        { field: f.email, message: "이메일을 입력하세요." },
        { field: f.name, message: "이름을 입력하세요." },
        { field: f.postcode, message: "우편번호를 입력하세요." },
        { field: f.address, message: "주소를 입력하세요." },
        { field: f.detailAddress, message: "상세 주소를 입력하세요." },
        { field: f.tel, message: "전화번호를 입력하세요." },
        { field: f.birthYear, message: "생년을 입력하세요." },
        { field: f.birthMonth, message: "생월을 입력하세요." },
        { field: f.birthDay, message: "생일을 입력하세요." }
    ];

    for (let i = 0; i < fields.length; i++) {
        let str = fields[i].field.value.trim();
        if (!str) {
            alert(`\n${fields[i].message}`);
            fields[i].field.focus();
            return false;
        }
        fields[i].field.value = str;
    }

    if (!isValidEmail(f.email.value)) {
        alert("\n정상적인 E-Mail을 입력하세요.");
        f.email.focus();
        return false;
    }

    if (f.password1.value !== f.password2.value) {
        alert("\n비밀번호가 일치하지 않습니다.");
        f.password2.focus();
        return false;
    }

    return true;
}
