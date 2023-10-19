var product_cost = 0;
var delivery_cost = 0;

$(document).ready(function(){

    var emoney = Number($('.possess .emph').text());

    $('.possess .emph').text(comma(emoney));
    
    //모두 사용 클릭시
    $(".emoney_chkbox").click(function(){
    
        var sum = product_cost+delivery_cost;
        
        if(emoney>sum){
        	$(".emoney_reg input").val(comma(sum));
        	emoney=sum;
        }else{
        	$(".emoney_reg input").val(comma(emoney));
        }
        
        
        $("#paper_reserves").text(" - " + comma(emoney)+ " 원");
        $(".emoney_point").val(emoney);
        product_price();
       
        
    })
    
	//포인트결제창 숫자입력시
	$("#emoney").on("input", function() {
    
	    var value = $(this).val().replace(/[^0-9]/g, '');
	
	    $(this).val(value);

	    var sum = product_cost + delivery_cost;
	    input_emoney = Number(value);

	    if (input_emoney >= sum) {
	        if (emoney >= sum) {
	            input_emoney = sum;
	        } else {
	            input_emoney = emoney;
	        }
	    } else {
	        if (input_emoney > emoney) {
	            input_emoney = emoney;
	        }
	    }

	    $("#emoney").val(comma(input_emoney));
	    $("#paper_reserves").text(" - " + comma(input_emoney) + " 원");
	    $(".emoney_point").val(input_emoney);
	    product_price();
	    
	});
	
	
    product_price();

	
	
});

//넘어온 상품들 가격합해서 결제금액창에 보여주기
function product_price(){

	product_cost = 0;
    delivery_cost = 0;
        
    $('.info_price .price').each(function() {
        
        var priceText = $(this).text();
        var price = parseFloat(priceText.replace(/[^0-9.-]+/g,""));
        product_cost += price;
        delivery_cost += 3000;
	});
	
    $("#productsTotalPrice").text(comma(product_cost));
	$("#paper_delivery").text(comma(delivery_cost));
	
    var emoney_point = Number($(".emoney_point").val());

    $("#paper_settlement").text(comma(product_cost + delivery_cost - emoney_point));

    $("#product_price_value").val(product_cost + delivery_cost - emoney_point);
    
}

//콤마찍는 함수
function comma(num){
    var len, point, str; 
       
    num = num + ""; 
    point = num.length % 3 ;
    len = num.length; 
   
    str = num.substring(0, point); 
    while (point < len) { 
        if (str != "") str += ","; 
        str += num.substring(point, point + 3); 
        point += 3; 
    } 
     
    return str;
 
}


//다음지도주소찾기
function execDaumPostcode() {
    new daum.Postcode({
        oncomplete: function(data) {
            // 팝업에서 검색결과 항목을 클릭했을때 실행할 코드를 작성하는 부분.

            // 각 주소의 노출 규칙에 따라 주소를 조합한다.
            // 내려오는 변수가 값이 없는 경우엔 공백('')값을 가지므로, 이를 참고하여 분기 한다.
            var addr = ''; // 주소 변수
            var extraAddr = ''; // 참고항목 변수

            //사용자가 선택한 주소 타입에 따라 해당 주소 값을 가져온다.
            if (data.userSelectedType === 'R') { // 사용자가 도로명 주소를 선택했을 경우
                addr = data.roadAddress;
            } else { // 사용자가 지번 주소를 선택했을 경우(J)
                addr = data.jibunAddress;
            }

            // 사용자가 선택한 주소가 도로명 타입일때 참고항목을 조합한다.
            if(data.userSelectedType === 'R'){
                // 법정동명이 있을 경우 추가한다. (법정리는 제외)
                // 법정동의 경우 마지막 문자가 "동/로/가"로 끝난다.
                if(data.bname !== '' && /[동|로|가]$/g.test(data.bname)){
                    extraAddr += data.bname;
                }
                // 건물명이 있고, 공동주택일 경우 추가한다.
                if(data.buildingName !== '' && data.apartment === 'Y'){
                    extraAddr += (extraAddr !== '' ? ', ' + data.buildingName : data.buildingName);
                }
                // 표시할 참고항목이 있을 경우, 괄호까지 추가한 최종 문자열을 만든다.
                if(extraAddr !== ''){
                    extraAddr = ' (' + extraAddr + ')';
                    addr += ', ' + extraAddr
                }
                // 조합된 참고항목을 해당 필드에 넣는다.
                
            
            } else {
                
            }

            // 우편번호와 주소 정보를 해당 필드에 넣는다.
            document.getElementById('postcode').value = data.zonecode;
            document.getElementById("address").value = addr;
            // 커서를 상세주소 필드로 이동한다.
            document.getElementById('postcode').disabled = true;
            document.getElementById('address').disabled = true;
            document.getElementById("detailAddress").focus();
        }
    }).open();
}




function requestPay() {

	var num =0;
	
	$('.name .inner_name').each(function() {
		num=num+1;
	});	
	
	var name = '';
	var nameElements = document.querySelectorAll(".inner_name");
	name = nameElements[0].textContent;
	
	if(num>=2){
		name += '외 '+(num-1)+'건';
	}
	
	var itemList = document.querySelectorAll(".list_product li"); 

	var itemIds = []; //id와 수량들어간 배열
	
	itemList.forEach(function (element) {
	    var itemId = element.getAttribute("data-id");
	    var count = element.getAttribute("data-count")
	    var itemData = {
	        id: itemId,
	        count: count
    	};
    	itemIds.push(itemData);
	});
		
	
	
	
	var merchant_uid = '';
	var randomInteger = Math.floor(Math.random() * 9) + 1;	
	var date = new Date();
	
	merchant_uid += date.getFullYear();
	merchant_uid += (date.getMonth()+1);
	merchant_uid += date.getDate()+'-';
	merchant_uid += Date.now();
	merchant_uid += randomInteger;
	
	var amount = $('#paper_settlement').text().replace(/[^0-9]/g, '');
	var buyer_email = $('#buyer_email').text();
	var buyer_name = $('#buyer_name').text();
	var buyer_tel = $('#buyer_tel').text();
	var buyer_postcode = $('#postcode').text();
	var buyer_addr = $('#address').text();
	var buyer_addr_detail = $('#detailAddress').text();

	var request = $('#request').text();
	
	IMP.init('imp56668363');
    IMP.request_pay({
    
	    pg : 'html5_inicis.INIpayTest', //테스트 시 html5_inicis.INIpayTest 기재 
        merchant_uid: merchant_uid,   // 주문번호
        name: name,
        amount: amount,               // 금액(숫자 타입)
        buyer_email: buyer_email,
        buyer_name: buyer_name,
        buyer_tel: buyer_tel,
        buyer_addr: buyer_addr,
        buyer_postcode: buyer_postcode
        
	}, function(rsp) { // callback 로직
		if (rsp.success) {
            
            var paymentData = {
            
	            imp_uid: rsp.pay_method,
	            merchant_uid: rsp.merchant_uid,
	            paid_amount: rsp.paid_amount,
	            apply_num: rsp.apply_num,
	            buyer_name: rsp.buyer_name,
	            buyer_tel: rsp.buyer_tel,
	            buyer_addr: rsp.buyer_addr,
	            buyer_addr_detail: rsp.buyer_addr_detail,
	            buyer_postcode: rsp.buyer_postcode,
	            itemIds: itemIds,
       		};
            var pay_method = rsp.pay_method;
            $.ajax({
	            type: "POST",
	            url: "/order/checkout",
	            contentType: "application/json", 
	            data: JSON.stringify(paymentData),
	            success: function (response) {
	                
	            	alert(pay_method);
	            	window.location.href = "/";
	            	
	            },
	            error: function (xhr, status, error) {
			        console.log(xhr.responseText);
			        alert("error");
			    }
            });
            
			

			
        } else {
        	var message = '결제에 실패했습니다.\n'+rsp.error_msg;
            alert(message);
        }
		
	});
	
  }




