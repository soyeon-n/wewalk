package com.spring.boot.dto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Entity
@SequenceGenerator(
		name = "PAYMENTS_INFO_SEQ_GENERATOR",
		sequenceName = "PAYMENTS_INFO_SEQ",
		initialValue = 1,
		allocationSize = 1)
public class PaymentsInfo {
	
	@Id
	@GeneratedValue(
			strategy = GenerationType.SEQUENCE,
			generator = "PAYMENTS_INFO_SEQ_GENERATOR"
			)
	private long paymentsNo;
	
	@Column(nullable = false, length = 100)
	private String payMethod;
	
	@Column(nullable = false, length = 100)
	private String impUid;
	
	@Column(nullable = false, length = 100)
	private String merchantUid;
	
	@Column(nullable = false)
	private int amount;
	
	@Column(nullable = false, length = 100)
	private String buyerAddr;
	
	@Column(nullable = false, length = 100)
	private String buyerPostcode;
	
	/*
	@OneToOne
	@JoinColumn(name = "actionBoardNo")
	private ActionBoard actionBoard;
	
	@ManyToOne
	@JoinColumn(name = "memberNo")
	private Member member;
	*/
}
