var product_cost = 0;
var delivery_cost = 0;


$(document).ready(function(){
    var emoney = Number($('.possess .emph').text());

    $('.possess .emph').text(comma(emoney));
    
    //적립금 사용
    $(".emoney_chkbox").click(function(){
    
        var sum = product_cost+delivery_cost;
        
        if(emoney>sum){
        	$(".emoney_reg input").val(comma(sum));
        }else{
        	$(".emoney_reg input").val(comma(emoney));
        }
        
        
        $("#paper_reserves").text(" - " + comma(emoney)+ " 원");
        $(".emoney_point").val(emoney);
        product_price();
       
        
    })

    $("#emoney").blur(function(){  
    	
    	var sum = product_cost+delivery_cost;
        input_emoney = Number($("#emoney").val());
        
        if (input_emoney >= sum){
        
        	if(emoney >= sum){
        	
        		input_emoney=sum;
        	
        	}else{
        	
        		input_emoney=emoney;
        	
        	}	
        
        }else{
        	
        	if(input_emoney>emoney){
        	
        		input_emoney=emoney;
        	
        	}
        }
        
        $("#emoney").val(comma(input_emoney));
        $("#paper_reserves").text(" - " + comma(input_emoney)+ " 원");
        $(".emoney_point").val(input_emoney);
        product_price();
       
        
    });

    

    product_price();
    
    
});


function product_price(){

	product_cost = 0;  //주문가격
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


function comma(num){          //콤마찍는 함수
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

