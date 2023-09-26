package com.spring.boot.dto;

public class ShippingForm {
	
	private String receivername;
	private String phone;
	private String zipcode;
    private String Address01;
    private String Address02;
    
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
		return Address01;
	}
	public void setAddress01(String address01) {
		Address01 = address01;
	}
	public String getAddress02() {
		return Address02;
	}
	public void setAddress02(String address02) {
		Address02 = address02;
	}


}
