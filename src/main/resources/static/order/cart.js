$(document).ready(function() {

	$('.btn.plus').click(function() {
	    increaseQuantity(this);
	    updateTotalPrice();
	    updateTotalAmount()
	});

	$('.btn.minus.off').click(function() {
	    decreaseQuantity(this);
	    updateTotalPrice();
	    updateTotalAmount()
	});
	
	$('.stepperCounter').on('input', function() {
        const input = $(this);
        const enteredValue = input.val().trim();
        const stock = parseInt(input.data('stock'));

         if (/^\d+$/.test(enteredValue)) {
            
            const enteredQuantity = parseInt(enteredValue);
            if (enteredQuantity > stock) {
                alert("재고부족! 현재재고는 " + stock + "개입니다.");
                input.val(stock);
            } else if (enteredQuantity <= 0) {
                
                input.val(1);
            }
            
        } else {
            
            input.val(1);
        }

        updateCartItem(this);
        input.trigger('change');

    });

	$('.stepperCounter, .checkOne,.btn.plus, .btn.minus.off').on('change', function() {
        updateTotalAmount();
        updateTotalPrice();
    });

	$('button[name="deleteOne"]').click(function() {
  		const item = $(this).closest('.item');
        const cartItemId = item.find('.stepperCounter.num').data('cartitemid');
        
        if (window.confirm('해당 상품을 삭제하시겠습니까?')){
        	deleteCartItem(cartItemId, item);
        	window.location.href = '/order/cart'
        }
        
        
        
	});
    
    $('#deleteSelected').click(function() {
        const selectedItems = [];

		//체크된항목의 id를 배열에담음
        $('.checkOne').each(function() {
	        if ($(this).is(':checked')) {
	            const cartItemId = $(this).closest('.item').find('.stepperCounter.num').data('cartitemid');
	            selectedItems.push(cartItemId);
	        }
   		 });


        if (selectedItems.length > 0) {
				
			if (window.confirm('선택된 '+selectedItems.length+'개의 상품을 삭제하시겠습니까?')){
 				
	            $.ajax({
	                url: '/order/cartItemSelDelete',
	                method: 'POST',
	                data: {
	                    cartItemIds: selectedItems,
	                },
	                success: function(response) {

	                    alert('삭제되었습니다.');
	                    
	                    window.location.href = '/order/cart'
	                },
	                error: function(xhr, status, error) {
	                   
	                }
	            });
            }
            
        } else {
            alert('선택된 상품이 없습니다.');
        }
    });
    
    $('.checkAll').click(function() {
	    $('.checkOne').prop('checked', $(this).is(':checked'));
	
	    updateTotalAmount()
	});

    
	updateTotalPrice();
	updateTotalAmount()

});


//체크된상품들 가격합표시
function updateTotalAmount() {
    let totalAmount = 0;
    $('.item').each(function () {
        const item = $(this);
        if (item.find('.checkOne').is(':checked')) {
            const productPrice = parseFloat(item.find('.selling').data('price'));

            const countInput = parseFloat(item.find('.stepperCounter.num').val());

            totalAmount += productPrice * countInput;
        }
    });

    $('.totalSum').text(totalAmount); 
    
    const totalAmountElement = $('.amount .totalPrice');
    totalAmountElement.text(numberWithComma(totalAmount));
}

//각 상품의 가격*갯수 금액표시
function updateTotalPrice() {
    $('.item').each(function() {
        const item = $(this);
        const productPrice = parseFloat(item.find('.selling').data('price'));
        const countInput = parseFloat(item.find('.stepperCounter.num').val());
        const totalPrice = productPrice * countInput;

        item.find('.selling').text(numberWithComma(totalPrice) + '원');
    });
}

//+버튼누를시
function increaseQuantity(button) {
    const input = $(button).siblings('.num');
    const currentValue = parseInt(input.val());
    const stock = parseInt(input.data('stock'));
    
    if (currentValue < stock) {
        input.val(currentValue + 1);
        updateCartItem(input);
        updateTotalPrice();
    } else {
        alert("재고부족! 현재재고는 " + stock + "개입니다.");
    }
    
}

//-버튼누를시
function decreaseQuantity(button) {
	const input = $(button).siblings('.num');
    const currentValue = parseInt(input.val());
  
	if (currentValue > 1) {
		input.val(currentValue - 1);
		updateCartItem(input);
		updateTotalPrice();
	}
}

//수량바뀔때 DB업데이트
function updateCartItem(input) {

    const count = parseInt($(input).val());
    const cartItemId = $(input).data('cartitemid');
   
    $.ajax({
        url: '/order/cartItemUpdate',
        method: 'POST',
        data: {
            cartItemId: cartItemId,
            count: count,
        },
        success: function(response) {
            
        },
        error: function(xhr, status, error) {

        }
    });

}

//x클릭시 삭제
function deleteCartItem(cartItemId, item) {
    $.ajax({
        url: '/order/cartItemDelete',
        method: 'POST',
        data: {
            cartItemId: cartItemId,
        },
        success: function(response) {
            item.remove();
            alert('삭제되었습니다.');
        },
        error: function(xhr, status, error) {
            console.error('Error deleting item:', error);
        }
    });
}

function numberWithComma(number) {
  return number.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
}

	