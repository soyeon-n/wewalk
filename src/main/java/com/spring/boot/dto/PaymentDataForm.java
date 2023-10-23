package com.spring.boot.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaymentDataForm {

    private String merchant_uid;
    private String name;
    private int paid_amount;
    private String pay_method;
    private String apply_num;
    private String buyer_name;
    private String buyer_tel;
    private String buyer_addr;
    private String buyer_addr_detail;
    private String buyer_postcode;
    private String request;
    private int pointPay;
    private int payMoney;
    private List<ItemDataForm> itemIds;
}

