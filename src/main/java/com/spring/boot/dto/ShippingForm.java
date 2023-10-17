package com.spring.boot.dto;

import javax.validation.constraints.NotNull;

public class ShippingForm {
	
	@NotNull(message = "받는 사람은 필수입니다.")
	private String receivername;
	
	@NotNull(message = "연락처는 필수입니다.")
	private String phone;
	private String zipcode;
	
	@NotNull(message = "주소 입력은 필수입니다.")
    private String address01;
    private String address02;
    
    @NotNull(message = "배송지 구분 입력은 필수입니다.")
    private String type;
    
	public String getReceivername() {
		return receivername;
	}
	public void setReceivername(String receivername) {
		this.receivername = receivername;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getZipcode() {
		return zipcode;
	}
	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}
	public String getAddress01() {
		return address01;
	}
	public void setAddress01(String address01) {
		this.address01 = address01;
	}
	public String getAddress02() {
		return address02;
	}
	public void setAddress02(String address02) {
		this.address02 = address02;
	}
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}


}
