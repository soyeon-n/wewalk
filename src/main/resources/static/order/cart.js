$(document).ready(function() {

	$('.btn.plus').click(function() {
	    increaseQuantity(this);
	    updateTotalPrice();
	});


	$('.btn.minus.off').click(function() {
	    decreaseQuantity(this);
	    updateTotalPrice();
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
        updateTotalPrice();
        updateTotalPrice();

    });

	$('.btn.plus, .btn.minus.off, .checkOne').on('change', function() {
        
        updateTotalAmount();
    });
    
	updateTotalPrice();
	updateTotalAmount()

});


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

    $('.totalSum').text(totalAmount); // Update the 'totalSum' element.
    
    // Add this section to update the 'Product amount' part.
    const totalAmountElement = $('.amount .totalPrice');
    totalAmountElement.text(totalAmount);
}


function updateTotalPrice() {
    $('.item').each(function() {
        const item = $(this);
        const productPrice = parseFloat(item.find('.selling').data('price'));
        const countInput = parseFloat(item.find('.stepperCounter.num').val());
        const totalPrice = productPrice * countInput;

        item.find('.selling').text(totalPrice + '원');
    });
}

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

function decreaseQuantity(button) {
	const input = $(button).siblings('.num');
    const currentValue = parseInt(input.val());
  
	if (currentValue > 1) {
		input.val(currentValue - 1);
		updateCartItem(input);
		updateTotalPrice();
	}
}

	
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


	
	