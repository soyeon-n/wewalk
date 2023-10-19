package com.spring.boot.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaymentDataForm {
	private String imp_uid;
    private String merchant_uid;
    private int paid_amount;
    private String apply_num;
    private String buyer_name;
    private String buyer_tel;
    private String buyer_addr;
    private String buyer_addr_detail;
    private String buyer_postcode;
    private List<ItemDataForm> itemIds;
}

