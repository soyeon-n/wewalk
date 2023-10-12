$(document).ready(function() {


	$('.btn.plus').click(function() {
	    increaseQuantity(this);
	});


	$('.btn.minus.off').click(function() {
	    decreaseQuantity(this);
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
    });

	
	
});



function increaseQuantity(button) {
    const input = $(button).siblings('.num');
    const currentValue = parseInt(input.val());
    const stock = parseInt(input.data('stock'));
    
    if (currentValue < stock) {
        input.val(currentValue + 1);
        updateCartItem(input);
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


	
	